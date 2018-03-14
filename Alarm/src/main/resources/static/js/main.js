$(function() {
	// 添加目标手机
	$('#ok-addPhone').click(function() {
		//saveRecord('addPhone-form', '/target/add');
		saveRecord('addPhone-form', '/target/add', function() {
			var smsPrice = $('#addPhone-form').find('input[name=smsPrice]').val();
			var prepayMoney = $('#addPhone-form').find('input[name=prepayMoney]').val();
			if(!/^\d(\.\d{1,3})?$/.test(smsPrice)) {
				alert('单价必须是0-1之间（元）');
			} else if(!/^\d{1,10}(\.\d{1,3})?$/.test(prepayMoney)) {
				alert('预付金额必须是正数');
			} else {
				saveRecord('addPhone-form', '/target/add');
			}
		});
		
	});
	// 添加目标手机重置按钮
	$("#rest-addPhone").click(function(){
		clearForm('addPhone-form');
	});
	// 修改目标手机确定按钮
	$('#ok-editPhone').click(function() {
		saveRecord('editPhone-form', '/target/edit', function() {
			var smsPrice = $('#editPhone-form').find('input[name=smsPrice]').val();
			var prepayMoney = $('#editPhone-form').find('input[name=prepayMoney]').val();
			if(!/^\d(\.\d{1,3})?$/.test(smsPrice)) {
				alert('单价必须是0-10元之间小数点后最多三位');
			}else if(!/^\d{1,10}(\.\d{1,3})?$/.test(prepayMoney)) {
				alert('预付金额必须是正数，小数点后最多三位');
			}else {
				saveRecord('editPhone-form', '/target/edit');
			}
		});
	});
	// 修改目标手机的重置按钮
	$("#reset-editPhone").click(function(){
	   clearForm('editPhone-form');
	});
	
	// 添加用户信息确定按钮
	$('#ok-adduser').click(function() {
		saveRecord('adduser-form', '/user/add');
	});
	// 修改用户信息确定按钮
	$('#ok-edituser').click(function() {
		saveRecord('edituser-form', '/user/edit');
	});
	// 添加用户重置按钮
	$("#reset-adduser").click(function(){
		clearForm('adduser-form');
	});
	// 修改用户的重置按钮
	$("#reset-edituser").click(function(){
	   clearForm('edituser-form');
	});
	
	// 添加前端设备信息确定按钮
	$('#ok-addfront').click(function() {
		saveRecord('addfront-form', '/front/add', function() {
			var checkCycle = $('#addfront-form').find('input[name=checkCycle]').val();
			var battery1 = $('#addfront-form').find('input[name=battery1]').val();
			var battery2 = $('#addfront-form').find('input[name=battery2]').val();
			var battery3 = $('#addfront-form').find('input[name=battery3]').val();
		    var check1 = /^\d+(\.{0,1}\d+){0,1}$/; // 非负数（0 和 正数）
		    var check2 = /^\d+$/; // 非负整数
			if(!check1.test(battery1)) {
				alert('电池1必须是大于0的数字');
			}else if(!check1.test(battery2)) {
				alert('电池2必须是大于0的数字');
			}else if(!check1.test(battery3)) {
				alert('电池3必须是大于0的数字');
			}else if(!check2.test(checkCycle)) {
				alert('自检周期必须是大于0的整数!');
			}else {
				saveRecord('addfront-form', '/front/add');
			}
		});
	});
	// 修改前端设备信息确定按钮
	$('#ok-editfront').click(function() {
		var checkCycle = $('#editfront-form').find('input[name=checkCycle]').val();
		 var check = /^\d+$/; // 非负整数
		 if(!check.test(checkCycle)) {
			alert('自检周期必须是大于0的整数!');
		 } else {
			saveRecord('editfront-form', '/front/edit');
		}
	});
	// 添加前端设备信息重置按钮
	$('#reset-addfront').click(function() {
		clearForm('addfront-form');
	});
	// 修改前端设备信息重置按钮
	$('#reset-editfront').click(function() {
		clearForm('editfront-form');
	});
});

// 重置用户密码
function resetPassword(id) {
	if(window.confirm('确定重置密码吗?')) {
		var url = getDomain() + "/user/rspass?id=" + id;
		$.get(url, function(data) {
			if(data === 'failure') {
				alert("重置密码失败!该用户可能不存在.");
			} else {
				alert("重置密码成功!新密码是" + data);
			}
		});
	}
}
// 删除记录
function deleteRecord(url, id) {
	if(!window.confirm('确定删除该条记录吗?')) {
		return;
	}
	var reqUrl = getDomain() + url + '?ids=' + id;
	$.get(reqUrl, function(data) {
		if(data === 'success') {
			top.location.reload();
		} else {
			alert('删除失败');
		}
	});
}

// 添加/保存
function saveRecord(formId, url, checkMethod) {
	if(checkForm(formId)) {
		if(checkMethod && !checkMethod()) {
			return;
		}
		var action = getDomain() + url;
		var reqStr = $('#' + formId).serialize();
		$.post(action, reqStr, function(data) {
			if(data === 'success') {
				top.location.reload();
			} else if(data === 'error1'){
				alert('网络请求超时!');
			} else if(data === 'error2'){
				alert('出现未知错误，数据添加失败！');
			} else {
				alert('添加失败!该记录可能已经存在.');
			}
		});
	}
}
