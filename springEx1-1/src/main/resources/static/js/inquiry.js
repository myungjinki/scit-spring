/**
 * 
 */

function formCheck() {
	let name = document.querySelector('#name').value.trim();
	let email = document.querySelector('#email').value.trim();
	let message = document.querySelector('#message').value.trim();
	
	/*event.preventDefault(); // 기본 제출 막기*/
	
	// 이름: 3 ~ 10자
	if (name.length < 3 || name.length > 10) {
		alert('이름은 3자 이상 10자 이하로 작성해주세요.');
		return false;
	}
	// 이메일: @ 포함
    if (!email.includes("@")) {
        alert("올바른 이메일 형식을 입력해주세요.");
        return false;
    }

    // 메시지: 5자 이상
    if (message.length < 5) {
        alert("문의 내용은 5자 이상 입력해주세요.");
        return false;
    }
	
	// 모든 조건 통과 시 true 반환 → form 제출
	alert('문의가 접수되었습니다!');
	return true;
	
	//event.target.submit(); // 수동으로 제출
	/* return boolean을 쓰면 안됨 return만 사용 */
}