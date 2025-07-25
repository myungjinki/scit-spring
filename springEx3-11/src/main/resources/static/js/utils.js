function deleteFunc(param, e) {
	console.log('param: ', param);
	
	const tId = e.target.getAttribute('data-tid');
	const tId2 = e.target.dataset.tid;
	
	console.log(tId, tId2);
	
	let result = confirm('정말 삭제하시겠습니까?');
	if (result) {
		location.href = 'student/delete?sid=' + param;
	}
}

function updateFunc(param, e) {
	console.log('param: ', param);
	
	const tId = e.target.getAttribute('data-tid');
	const tId2 = e.target.dataset.tid;
	
	location.href = 'student/update?sid=' + param;
}

function check() {
	let name = document.querySelector('#name');
	let major = document.querySelector('#major');
	let java = document.querySelector('#java');
	let db = document.querySelector('#db');
	let web = document.querySelector('#web');
	
	if (name.value.length < 3 || name.value.length > 10) {
		alert('이름은 3자 이상 10자로 입력해주세요.');
		name.focus();
		name.select();
		return false;
	}
	if (major.value == '') {
		alert('전공을 입력해주세요.');
		major.focus();
		major.select();
		return false;
	}
	if (java.value == '') {
		alert('자바 등급을 선택해주세요');
		return false;
	}
	if (db.value == '') {
		alert('DB 등급을 선택해주세요');
		return false;
	}
	if (web.value == '') {
		alert('WEB 등급을 선택해주세요');
		return false;
	}
	return true;
}