/* writeForm.js */

window.onload = function() {
	let h2 = document.querySelector('h2');
	
	h2.addEventListener('click', function() {
		location.href = '/web4';
	});
}

function checkForm() {
	let name = document.querySelector('#name');
	let password = document.querySelector('#password');
	
	if (name.value.length < 5) {
		alert('이름은 5자 이상 입력하세요');
		name.focus();
		name.select();
		return false;
	}
	if (password.value.length < 5) {
		alert('비밀번호는 5자 이상 입력하세요');
		password.focus();
		password.select();
		return false;
	}
	return true;
}