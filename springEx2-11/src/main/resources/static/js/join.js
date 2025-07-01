window.onload = function() {
	
	document.querySelector('form').onsubmit = function(e) {
		const id = document.querySelector('#id').value.trim();
		const pw1 = document.querySelector('#pw1').value.trim();
		const pw2 = document.querySelector('#pw2').value.trim();
		const name = document.querySelector('#name').value.trim();
		const phone = document.querySelector('#phone').value.trim();
		
		// 아이디 유효성 검사: 3~14자, 영문/숫자/특수문자 포함 허용
		const idRegex = /^[a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]{3,14}$/;
		// ID 유효성 검사 (3 ~ 14)
		if (id.length < 3 || id.length >14) {
			alert('아이디는 3~14자 사이')
			e.preventDefault(); // 폼 제출 취소, 전송은 안되고 계속 JavaScript 코드가 진행됨
			return false;
		}
		
		// 비밀번호 일치 검사
		// 빈 문자열 또는 null/undefined일 때 false
		if (!pw1 || !pw2 || pw1 !== pw2) {
			alert('비밀번호가 비어 있거나 일치하지 않습니다.')
			e.preventDefault(); // 폼 제출 취소
			return false;			
		}
		
		// 이름 필수 입력
		if (name === "") {
			alert('이름을 입력해주세요.')
			e.preventDefault(); // 폼 제출 취소
			return false;						
		}

		// 핸드폰 필수 입력
		if (phone === "") {
			alert('핸드폰 번호를 입력해주세요.')
			e.preventDefault(); // 폼 제출 취소
			return false;						
		}
	}
}