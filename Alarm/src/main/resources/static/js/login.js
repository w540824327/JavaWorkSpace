$(function () {
    toScrollTopCenter();
    toScrollLeftCenter();
    window.onresize = function () {
        toScrollTopCenter();
        toScrollLeftCenter();
    };
});

function toScrollLeftCenter() {
    var clientWidth = self.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var offsetWidth = getOffsetWidth(document.querySelector(".login-content"));
    if (clientWidth < 1200) {
        clientWidth = 1200;
        document.body.style.overflowX = "visible";
        document.body.style.width = 1200 + 'px';
    } else {
        document.body.style.width = clientWidth + 'px';
        document.body.style.overflowX = "hidden";
    }
    clientWidth = (clientWidth - offsetWidth) / 2;
    document.querySelector(".left-rect").style.width = (clientWidth - 180) + 'px';
    document.querySelector(".right-rect").style.marginLeft = '180px';
    document.querySelector(".right-rect").style.width = (clientWidth - 180) + 'px';
}

function toScrollTopCenter() {
    var clientHeight = self.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
    var offsetHeight = getOffsetHeight(document.querySelector(".login-content"));
    var headerHeight = getOffsetHeight(document.querySelector('.login-header'));
    if (clientHeight < 600) {
        clientHeight = 600;
        document.body.style.overflowY = 'visible';
        document.body.style.height = '600px';
    } else {
        document.body.style.overflowY = "hidden";
        document.body.style.height = clientHeight + 'px';
    }
    clientHeight = (clientHeight - offsetHeight) / 2;
    document.querySelector('.login-main').style.marginTop = (clientHeight - headerHeight) + 'px';
}

function getOffsetWidth(element) {
    var currentStyle = element.currentStyle || window.getComputedStyle(element, null);
    if (currentStyle.display === 'none') {
        var cloneDom = element.cloneNode(true);
        cloneDom.style.cssText = 'position: absolute; display: block; top: -3000px;';
        document.body.appendChild(cloneDom);
        var offsetWidth = cloneDom['offsetWidth'];
        cloneDom.parentNode.removeChild(cloneDom);
        return offsetWidth;
    }
    return element['offsetWidth'];
};

function getOffsetHeight(element) {
    var currentStyle = element.currentStyle || window.getComputedStyle(element, null);
    if (currentStyle.display === 'none') {
        var cloneDom = element.cloneNode(true);
        cloneDom.style.cssText = 'position: absolute; display: block; top: -3000px;';
        document.body.appendChild(cloneDom);
        var offsetHeight = cloneDom['offsetHeight'];
        cloneDom.parentNode.removeChild(cloneDom);
        return offsetHeight;
    }
    return element['offsetHeight'];
};

function getDomain() {
	var protocol = location.protocol;
	var host = location.host;
	return protocol + '//' + host;
}

$(function() {
	// 验证码
	$('#login-checkcode').click(function() {
		var src = $(this).attr('src');
		var index = src.indexOf('?');
		if(index != -1) {
			src = src.substring(0, index);
		}
		src += '?r=' + Math.random();
		$(this).attr("src", src);
	});
	
	// 登录
	$('#login-btn').click(function() {
		checkLogin();
	});
	document.onkeydown = function(event) {
		if(event.keyCode == 13) {
			checkLogin();
		}
	};
});

function checkLogin() {
	var checkCode = $('input[name=checkCode]').val();
	if(checkCode == null || checkCode.trim().length == 0) {
		alert('请输入验证码');
		return;
	}
	var username = $('input[name=username]').val();
	if(username == null || username.trim().length == 0) {
		alert('请输入用户名');
		$('input[name=checkCode]').val('');
		$('#login-checkcode').trigger('click');
		return;
	}
	var password = $('input[name=password]').val();
	if(password == null || password.trim().length == 0) {
		alert('请输入密码');
		$('input[name=checkCode]').val('');
		$('#login-checkcode').trigger('click');
		return;
	}
	var userType = $('input[name=userType]').val();
	if(userType == null || userType.trim().length == 0) {
		alert('请选择用户类型');
		$('input[name=checkCode]').val('');
		$('#login-checkcode').trigger('click');
		return;
	}
	var action = $('#login-form').attr('action');
	var reqStr = $('#login-form').serialize();
	$.post(action, reqStr, function(data) {
		if(data === 'success') {
			top.window.location = getDomain() + '/alarm/record';
		} else {
			if(data === 'error1'){
				alert('用户类型,用户名,密码,验证码都是必填项');
			} else if(data === 'error2') {
				alert('验证码错误');
			} else if(data === 'error3') {
				alert('用户名或密码或用户类型错误');
			} else {
				alert('服务器忙...');
			}
			$('input[name=checkCode]').val('');
			$('#login-checkcode').trigger('click');
		}
	});
}
