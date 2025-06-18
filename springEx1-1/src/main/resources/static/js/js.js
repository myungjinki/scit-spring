document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form'); // 폼 요소를 가져옵니다.

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

        // 입력 필드 요소들을 가져옵니다.
        const nameInput = document.getElementById('name');
        const emailInput = document.getElementById('email');
        const inqueryInput = document.getElementById('inquery');

        let isValid = true; // 유효성 검사 통과 여부를 나타내는 플래그

        // 1. 이름 필드 유효성 검사
        if (nameInput.value.trim() === '') { // 입력값이 비어있거나 공백만 있는 경우
            alert('이름을 입력해주세요.');
            nameInput.focus(); // 해당 필드로 포커스 이동
            isValid = false;
            return; // 추가 검사 없이 함수 종료
        }

        // 2. 이메일 필드 유효성 검사
        if (emailInput.value.trim() === '') {
            alert('이메일을 입력해주세요.');
            emailInput.focus();
            isValid = false;
            return;
        } else if (!isValidEmail(emailInput.value)) { // 이메일 형식 검사 함수 호출
            alert('유효한 이메일 주소를 입력해주세요.');
            emailInput.focus();
            isValid = false;
            return;
        }

        // 3. 문의 내용 필드 유효성 검사
        if (inqueryInput.value.trim() === '') {
            alert('문의 내용을 입력해주세요.');
            inqueryInput.focus();
            isValid = false;
            return;
        }

        // 모든 유효성 검사를 통과했을 때
        if (isValid) {
            alert('폼이 성공적으로 제출되었습니다!');
            // 실제 서버로 폼을 제출하려면 아래 주석을 해제하세요.
            form.submit();
        }
    });

    // 이메일 형식 검사를 위한 정규식 함수
    function isValidEmail(email) {
        // 간단한 이메일 정규식 (더 복잡한 검사는 필요에 따라 추가)
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
});