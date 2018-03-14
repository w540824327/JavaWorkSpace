var myDialog = (function() {
    var maskDiv, contentDiv, headerDiv, headerInnerDiv, closeBtn, bodyDiv, customDiv, footerDiv;// 文档结构Div
    var offsetWidth, offsetHeight;// contentDiv的offsetWidth,offsetHeight
    var bodyStyle;// body的style
    var dialogTimer = null, scrollbarTimer = null;// dialog关闭的计时器，滚动条显示的计时器
    var myUtils = new MyUtils();
    var firstShow = true;
    return {
        show: function(options) {// 显示dialog
            options = options || {};

            // 清除滚动条显示的计时器
            clearTimeout(scrollbarTimer);
            
            // 记录body原始的style
            bodyStyle = myUtils.getCurrentStyle(document.body).cssText;

            // 创建dialog结构
            if(firstShow) {
            	maskDiv = myUtils.createElement({tagName: 'div', className: 'dialog-mask'});
                contentDiv = myUtils.createElement({tagName: 'div', className: 'dialog-content'});
                headerDiv = myUtils.createElement({tagName: 'div', className: 'dialog-header'});
                headerInnerDiv = myUtils.createElement({tagName: 'div', className: 'dialog-header-inner'});
                bodyDiv = myUtils.createElement({tagName: 'div', className: 'dialog-body'});
                //footerDiv = myUtils.createElement({tagName: 'div', className: 'dialog-footer'});

                headerDiv.appendChild(headerInnerDiv);
                contentDiv.appendChild(headerDiv);
                contentDiv.appendChild(bodyDiv);
                //contentDiv.appendChild(footerDiv);
                document.body.appendChild(maskDiv);
                document.body.appendChild(contentDiv);
                firstShow = false;
            }

            if(options.closable == null || options.closable === true) {// 是否显示右上角的关闭按钮，默认：true
                closeBtn = myUtils.createElement({tagName: 'span', className: 'dialog-close'});
                contentDiv.appendChild(closeBtn);
            }

            // 加载用户设置的Div或者加载dialog内容
            if(options.eleId && document.getElementById(options.eleId)) {
                customDiv = document.getElementById(options.eleId);
                customDiv.style.display = 'block';
                bodyDiv.appendChild(customDiv);
            } else {
                if(options.content) {
                    bodyDiv.innerHTML = options.content;
                } else {
                    bodyDiv.innerHTML = '<span>这是消息窗口</span>';
                }
            }

            // 解析参数
            if(options.title) {// 标题
                headerInnerDiv.innerHTML = options.title;
            } else {
                headerInnerDiv.innerHTML = '消息窗口';// 默认标题
            }
            if(options.width && !isNaN(options.width)) {// dialog宽度
                contentDiv.style.width = options.width + 'px';
            }
            if(options.height && !isNaN(options.height)) {// dialog高度
                contentDiv.style.height = options.height + 'px';
            }
            if(options.left && !isNaN(options.left)) {// dialog的left
                contentDiv.style.left = options.left + 'px';
            }
            if(options.top && !isNaN(options.top)) {// dialog的top
                contentDiv.style.top = options.top + 'px';
            }
            if(options.mask == null || options.mask === true) {//  点击遮罩层关闭，默认：true
                if(options.onmask) {
                    maskDiv.onclick = options.onmask;
                } else {
                    maskDiv.onclick = myDialog.hide;
                }
            }
            if(options.resize == null || options.resize === true) {// 窗口改变时dialog是否保持原来位置，默认：true
                window.onresize = function() {
                    myDialog.init(options.center, options.left, options.top);
                };
            }
            if(options.drag == null || options.drag === true) {// 是否可以拖动，默认：true
                headerDiv.style.cursor = 'move';
                this.drag(contentDiv);
            }
            if(options.timeout && !isNaN(options.timeout)) {// 设置一段时间后自动关闭（单位：ms）
                dialogTimer = setTimeout(function() {
                    myDialog.hide();
                }, options.timeout);
            }
            if(options != null && options.scroll === true) {// 背景是否可以滚动，默认：false
                window.onscroll = myDialog.scroll;
            } else {
                // 获取滚动条宽度
                var scrollbarWidth = myUtils.getScrollbarWidth();
                document.body.style.cssText = 'overflow: hidden; padding-right: ' + scrollbarWidth + 'px';
            }

            // 添加事件
            if(closeBtn) {
                closeBtn.onclick = function() {
                    if(options.onclose) {// 自定义右上角关闭按钮事件
                        options.onclose();
                    } else {
                        myDialog.hide();
                    }
                }
            }

            offsetWidth = myUtils.getOffsetWidth(contentDiv);
            offsetHeight = myUtils.getOffsetHeight(contentDiv);
            myDialog.init(options.center, options.left, options.top);
            myDialog.alpha(maskDiv, 50, 1);
        },
        hide: function() {// 隐藏dialog
            clearTimeout(dialogTimer);
            if(customDiv) {
            	customDiv.style.display = 'none';
            	document.body.appendChild(customDiv);
            }
            myDialog.alpha(contentDiv, 0, -1);
            scrollbarTimer = setTimeout(function() {
                document.body.style.cssText = bodyStyle;
            }, 500);
        },
        init: function(center, x, y) {// 初始化dialog
            maskDiv.style.height = myUtils.getMaxClientHeight() + 'px';
            maskDiv.style.width = myUtils.getMaxClientWidth() + 'px';
            contentDiv.style.left = (myUtils.getClientWidth() - offsetWidth) / 2 + 'px';
            if(center) {
                var height = (myUtils.getClientHeight() - offsetHeight) / 2;
                contentDiv.style.top = (height + myUtils.getScrollTop()) + 'px';
            } else {
                contentDiv.style.top = (100 + myUtils.getScrollTop()) + 'px';
                if(x) {
                    contentDiv.style.left = x + 'px';
                }
                if(y) {
                    contentDiv.style.top = y + 'px';
                }
            }
        },
        scroll: function() {// 滚动背景时，保持dialog位置
            var total = myUtils.getCurrentStyle(contentDiv).top;
            total = total.substring(0, total.length - 1);
            contentDiv.style.top = (total + myUtils.getScrollTop()) + 'px';
        },
        alpha: function(element, a, d) {
            clearInterval(element.ai);
            if(d === 1) {
                element.style.opacity = '0';
                element.style.filter = 'alpha(opacity=0)';
                element.style.display = 'block';
            }
            element.ai = setInterval(function() {
                myDialog.ta(element, a, d);
            }, 9);
        },
        ta: function(element, a, d) {
            var anum = Math.round(element.style.opacity * 100);
            if(anum === a) {
                clearInterval(element.ai);
                if(d === -1) {
                    element.style.display = 'none';
                    if(element === contentDiv) {
                        this.alpha(maskDiv, 0, -1);
                    }
                } else {
                    if(element === maskDiv) {
                        this.alpha(contentDiv, 100, 1);
                    }
                }
            } else {
                var n = Math.ceil(anum + ((a - anum) * .5));
                n = (n === 1) ? 0 : n;
                element.style.opacity = (n / 100) + '';
                element.style.filter = 'alpha(opacity=' + n + ')';
            }
        },
        drag: function(element) {// 拖动dialog
            var startX, startY;
            var mouse = {
                mouseup: function() {
                    if(element.releaseCapture) {
                        element.onmousemove = null;
                        element.onmouseup = null;
                        element.releaseCapture();
                    } else {
                        document.removeEventListener('mousemove', mouse.mousemove, true);
                        document.removeEventListener('mouseup', mouse.mouseup, true);
                    }
                },
                mousemove: function(ev) {
                    var oEvent = ev || event;
                    if(oEvent.clientX - startX >= 0 && oEvent.clientX - startX <= myUtils.getClientWidth() - contentDiv.offsetWidth) {
                        element.style.left = (oEvent.clientX - startX) + 'px';
                    }
                    if(oEvent.clientY - startY >= 0 && oEvent.clientY - startY <= myUtils.getClientHeight() - contentDiv.offsetHeight) {
                        element.style.top = (oEvent.clientY - startY) + 'px';
                    }
                }
            };
            element.onmousedown = function(ev) {
                var oEvent = ev || event;
                startX = oEvent.clientX - element.offsetLeft;
                startY = oEvent.clientY - element.offsetTop;
                if(startY > headerDiv.offsetHeight) {
                    return;
                }
                if(element.setCapture) {
                    element.onmousemove = mouse.mousemove;
                    element.onmouseup = mouse.mouseup;
                    element.setCapture();
                } else {
                    document.addEventListener('mousemove', mouse.mousemove, true);
                    document.addEventListener('mouseup', mouse.mouseup, true);
                }
            }
        }
    };
})();


/**
 * MyUtils 工具类
 * @constructor
 * @author lmkang25@163.com
 */
function MyUtils() {}

/**
 * 获取浏览器滚动条宽度
 * @returns {number}
 */
MyUtils.prototype.getScrollbarWidth = function() {
    var width1 = document.body.offsetWidth;
    var cssText = document.body.style.cssText;
    document.body.style.cssText = 'overflow: hidden !important;';
    var width2 = document.body.offsetWidth;
    document.body.style.cssText = cssText;
    return width2 - width1;
};

/**
 * 获取元素当前的样式
 * @param element
 * @returns {*|CSSStyleDeclaration}
 */
MyUtils.prototype.getCurrentStyle = function(element) {
    return element.currentStyle || window.getComputedStyle(element, null);
};

/**
 * 获取元素的offsetWidth
 * @param element
 * @returns {*}
 */
MyUtils.prototype.getOffsetWidth = function(element) {
    var currentStyle = this.getCurrentStyle(element);
    if(currentStyle.display === 'none') {
        var cloneDom = element.cloneNode(true);
        cloneDom.style.cssText = 'position: absolute; display: block; top: -3000px;';
        document.body.appendChild(cloneDom);
        var offsetWidth = cloneDom['offsetWidth'];
        cloneDom.parentNode.removeChild(cloneDom);
        return offsetWidth;
    }
    return element['offsetWidth'];
};

/**
 * 获取元素的offsetHeight
 * @param element
 * @returns {*}
 */
MyUtils.prototype.getOffsetHeight = function(element) {
    var currentStyle = this.getCurrentStyle(element);
    if(currentStyle.display === 'none') {
        var cloneDom = element.cloneNode(true);
        cloneDom.style.cssText = 'position: absolute; display: block; top: -3000px;';
        document.body.appendChild(cloneDom);
        var offsetLen = cloneDom['offsetHeight'];
        cloneDom.parentNode.removeChild(cloneDom);
        return offsetLen;
    }
    return element['offsetHeight'];
};

/**
 * 获取垂直滚动条滚动的距离
 * @returns {number}
 */
MyUtils.prototype.getScrollTop = function() {
    return document.documentElement.scrollTop || document.body.scrollTop;
};

/**
 * 获取浏览器显示区域宽度，不包括滚动条
 * @returns {number}
 */
MyUtils.prototype.getClientWidth = function() {
    return self.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
};

/**
 *获取浏览器显示区域高度，不包括滚动条
 * @returns {number}
 */
MyUtils.prototype.getClientHeight = function() {
    return self.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
};

/**
 * 获取浏览器可视区域的最大宽度
 * @returns {number}
 */
MyUtils.prototype.getMaxClientWidth = function() {
    var b = document.body;
    var e = document.documentElement;
    return Math.max(Math.max(b.scrollWidth, e.scrollWidth), Math.max(b.clientWidth, e.clientWidth));
};

/**
 * 获取浏览器可视区域的最大高度
 * @returns {number}
 */
MyUtils.prototype.getMaxClientHeight = function() {
    var b = document.body;
    var e = document.documentElement;
    return Math.max(Math.max(b.scrollHeight, e.scrollHeight), Math.max(b.clientHeight, e.clientHeight));
};

/**
 * 创建元素
 * @param args
 * @returns {*}
 */
MyUtils.prototype.createElement = function(args) {
    if(args.tagName) {// 标签名
        var ele = document.createElement(args.tagName);
        if(args.id) {// id
            ele.id = args.id;
        }
        if(args.className) {// css类名
            ele.className = args.className;
        }
        return ele;
    } else {
        return null;
    }
};

/**
 * 解决命名冲突
 * @returns {{show, hide, init, scroll, alpha, ta, drag}}
 */
MyUtils.prototype.noConflict = function() {
    return myDialog;
};