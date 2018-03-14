$(function() {
	// 重置密码
	$('#reset-pass').click(function() {
		clearForm('editpass-form');
	});
	// 确定修改密码
	$('#ok-pass').click(function() {
		if(checkForm('editpass-form')) {
			var action = getDomain() + "/user/editpass";
			var reqStr = $('#editpass-form').serialize();
			$.post(action, reqStr, function(data) {
				if(data === 'success') {
					alert('修改密码成功,请重新登录!');
					top.window.location = getDomain() + "/user/logout";
				} else if(data === 'error1'){
					alert('旧密码,新密码,确认密码都是必填项');
				} else if(data === 'error2') {
					alert('新密码和确认密码不一致');
				} else if(data === 'error3'){
					alert('旧密码错误');
				} else {
					alert('修改密码失败');
				}
			});
		}
	});
	
	// 全选/反选
	$('#selectall-checkbox').click(function() {
		if($('#selectall-checkbox').attr('checked')) {
			$('#datalist-table').find('input[type=checkbox]').attr('checked', 'checked');
		} else {
			$('#datalist-table').find('input[type=checkbox]').removeAttr('checked');
		}
	});
});

// 批量删除
function deleteDataList(url) {
	if(!window.confirm('确定批量删除数据吗?')) {
		return;
	}
	var checkboxList = $('#datalist-table').find('input[type=checkbox]');
	var ids = '';
	if(checkboxList != null) {
		for(var i = 0; i < checkboxList.length; i++) {
			var checkbox = checkboxList[i];
			if($(checkbox).attr('checked')) {
				var value = $(checkbox).val();
				if(value != null && value.trim().length > 0 
						&& value.trim().toLowerCase() !== 'on') {
					ids += value + ',';
				}
			}
		}
		if(ids != null && ids.length > 0) {
			ids = ids.substring(0, ids.length - 1);
			url = getDomain() + url + '?ids=' + ids;
			$.get(url, function(data) {
				if(data === 'success') {
					top.location.reload();
				} else {
					alert('批量删除失败');
				}
			});
		} else {
			alert('请至少选择一项');
		}
	}
}

// 分页跳转
function gotoPage(pageNum) {
	var url = new String(top.window.location);
	var index = url.indexOf('page');
	if(index != -1) {
		var tmp = url.substring(0, index);
		url = tmp + 'page=' + pageNum;
	} else {
		var i = url.indexOf('?');
		if(i != -1) {
			url += '&page=' +pageNum;
		} else {
			url += '?page=' + pageNum;
		}
	}
	location = url;
}

//校验表单非空
function checkForm(formId) {
	var flag = true;
	var inputList = $('#' + formId).find('input[required=required]');
	if(inputList != null && inputList.length > 0) {
		for(var i = 0; i < inputList.length; i++) {
			(function() {
				var value = $(inputList[i]).val();
				if(value == null || value.trim().length == 0) {
					flag = false;
					$(inputList[i]).val('');// 触发required='required'
					$(inputList[i]).focus();
				}
				$(inputList[i]).on("keyup,blur", function() {
					var value = $(inputList[i]).val();
					if(value != null || value.trim().length > 0) {
						$(inputList[i]).blur();
					}
				});
			})(i);
			if(!flag) {
				break;
			}
		};
	}
	return flag;
}

// 清空表单数据
function clearForm(formId) {
	var inputList = $('#' + formId).find('input,select');
	if(inputList != null && inputList.length > 0) {
		for(var i = 0; i < inputList.length; i++) {
			var value = $(inputList[i]).val();
			if(value != null || value.trim().length > 0) {
				$(inputList[i]).val('');
			}
		}
	}
}

// 获取当前日期
function getCurrentDate() {
	var date = new Date();
	return date.toLocaleDateString();
}

//格式化日期
function formatDate(time, formatter) {
	var date = new Date(time);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(month < 10) {
		month = "0" + month;
	}
	var day = date.getDate();
	if(day < 10) {
		day = "0" + day;
	}
	return year + formatter + month + formatter + day;
}
// 格式化成 'yyyy-MM-dd HH:mm:ss' 的日期格式
function myFormat(data) {
	var date = new Date(data);
	var year = date.getFullYear()+'-';
	var month = date.getMonth() + 1;
	if(month < 10) {
		month = "0" + month;
	}
	var day = date.getDate();
	if(day < 10) {
		day = "0" + day;
	}
	var hours = date.getHours();
	if(hours < 10) {
		hours = "0" + hours;
	}
	var minute = date.getMinutes();
	if(minute < 10) {
		minute = "0" + minute;
	}
	var second = date.getSeconds();
	if(second < 10) {
		second = "0" + second;
	}
	var okTime = year+month+'-'+day+' '+hours+':'+minute+':'+second;
    return okTime;
}
// 获取域名
function getDomain() {
	var protocol = location.protocol;
	var host = location.host;
	return protocol + '//' + host;
}

//修改密码弹窗
function editPasswordDlg() {
    myDialog.show({
        eleId: 'editPassword-dlg',
        title: '修改密码',
        width: 360
    });
}

// 安全退出
function logout() {
	var domain = getDomain();
	top.window.location = domain + "/user/logout";
}


// ======窗口大小事件监听========
$(function() {
	myOnresize();
	window.onresize = myOnresize;
});

function myOnresize() {
    var clientWidth = self.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    if(clientWidth < 1200) {
        document.body.style.overflowX = 'visible';
        clientWidth = 1200;
    } else {
        document.body.style.overflowX = 'hidden';
    }
    document.body.style.width = clientWidth + 'px';
    // 这句很关键，在页面最大的div设置id='alarm-app'
    document.getElementById('alarm-app').style.width = clientWidth + 'px';
    var total = clientWidth - 70;
    document.querySelector('.xthead_left').style.width = (Math.round(total * 0.2)) + 'px';
	document.querySelector('.xthead_right').style.width = (Math.round(total * 0.8)) + 'px';
	document.querySelector('.xthead_right').style.marginLeft = (Math.round(total * 0.2) + 70) + 'px';
}