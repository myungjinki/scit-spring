function orderFunc() {
	// HTML에서 id를 기반으로 필요한 DOM 요소들을 가져옵니다.
	const chickenTypeSelect = document.getElementById('chickenType');
	const quantityInput = document.getElementById('quantity');
	const extraOptionCheckboxes = document.querySelectorAll('input[name="extraOption"]:checked');
	const deliveryTypeRadio = document.querySelector('input[name="deliveryType"]:checked');
	const errorDiv = document.getElementById('error');
	const orderForm = document.getElementById('orderForm');

	// 이전에 표시되었을 수 있는 오류 메시지를 초기화합니다.
	errorDiv.textContent = '';

	// 치킨 종류와 수량 입력값의 유효성을 검사합니다.
	const chickenPrice = parseInt(chickenTypeSelect.value, 10); // select의 value는 문자열이므로 숫자로 변환합니다.
	const quantity = parseInt(quantityInput.value, 10); // input의 value는 문자열이므로 숫자로 변환합니다.

	// 치킨 종류가 선택되지 않았는지 확인합니다.
	if (isNaN(chickenPrice) || chickenPrice <= 0) {
		errorDiv.textContent = '치킨 종류를 선택해주세요.'; // 오류 메시지를 표시합니다.
		chickenTypeSelect.focus(); // 사용자가 쉽게 수정할 수 있도록 해당 필드에 포커스를 줍니다.
		return; // 유효성 검사에 실패했으므로 함수 실행을 중단합니다.
	}

	// 수량이 1 이상인지 확인합니다.
	if (isNaN(quantity) || quantity < 1) {
		errorDiv.textContent = '수량을 1 이상으로 입력해주세요.'; // 오류 메시지를 표시합니다.
		quantityInput.focus(); // 해당 필드에 포커스를 줍니다.
		return; // 함수 실행을 중단합니다.
	}

	// 추가 메뉴 가격을 계산합니다.
	let extraTotalPrice = 0;
	const extraOptions = []; // 서버로 전송될 추가 메뉴 이름 배열
	const extraOptionsLabels = []; // 영수증에 표시될 추가 메뉴 이름 배열
	// 체크된 모든 추가 메뉴 체크박스를 순회합니다.
	extraOptionCheckboxes.forEach(checkbox => {
		extraTotalPrice += parseInt(checkbox.value, 10); // 각 추가 메뉴의 가격을 더합니다.
		const label = checkbox.nextElementSibling; // 체크박스 바로 다음에 오는 label 요소를 찾습니다.
		extraOptions.push(label.textContent.trim()); // label의 텍스트를 배열에 추가합니다.
		extraOptionsLabels.push(label.textContent.trim());
	});

	// 배달 비용을 가져옵니다.
	const deliveryPrice = parseInt(deliveryTypeRadio.value, 10); // 선택된 배달 방식의 가격을 숫자로 변환합니다.
	const deliveryLabel = deliveryTypeRadio.nextElementSibling.textContent; // 선택된 배달 방식의 라벨 텍스트를 가져옵니다.

	// 최종 결제 금액을 계산합니다.
	const chickenBasePrice = chickenPrice * quantity; // (치킨 단가 * 수량)
	const totalPrice = chickenBasePrice + extraTotalPrice + deliveryPrice; // (치킨 가격 + 추가 메뉴 가격 + 배달비)

	// 서버로 전송할 form의 hidden input 필드에 계산된 값들을 채워넣습니다.
	const chickenTypeText = chickenTypeSelect.options[chickenTypeSelect.selectedIndex].text; // 선택된 치킨의 전체 텍스트를 가져옵니다.
	
	orderForm.elements['chickenType'].value = chickenTypeText;
	orderForm.elements['chickenPrice'].value = chickenBasePrice;
	orderForm.elements['quantity'].value = quantity;
	orderForm.elements['extraOptions'].value = extraOptions.join(', '); // 배열을 쉼표로 구분된 문자열로 변환하여 저장합니다.
	orderForm.elements['extraTotalPrice'].value = extraTotalPrice;
	orderForm.elements['deliveryType'].value = deliveryLabel;
	orderForm.elements['deliveryPrice'].value = deliveryPrice;
	orderForm.elements['totalPrice'].value = totalPrice;

	// 영수증 모달에 주문 내역을 채워넣습니다.
	document.getElementById('receiptChickenType').textContent = `${chickenTypeText} (${chickenBasePrice.toLocaleString()}원)`;
	document.getElementById('receiptQuantity').textContent = `${quantity}개`;
	
	const receiptExtrasDiv = document.getElementById('receiptExtras');
	// 선택된 추가 메뉴가 있는지 확인합니다.
	if (extraOptionsLabels.length > 0) {
		// 추가 메뉴가 있으면 각 항목을 줄바꿈(<br>)으로 연결하고 총 추가 금액을 표시합니다.
		receiptExtrasDiv.innerHTML = extraOptionsLabels.join('<br>') + ` (+${extraTotalPrice.toLocaleString()}원)`;
	} else {
		// 추가 메뉴가 없으면 '없음'으로 표시합니다.
		receiptExtrasDiv.textContent = '없음';
	}

	document.getElementById('receiptDelivery').textContent = `${deliveryLabel} (+${deliveryPrice.toLocaleString()}원)`;
	document.getElementById('receiptTotal').textContent = `${totalPrice.toLocaleString()}원`; // toLocaleString()으로 숫자에 3자리 콤마를 추가합니다.

	// 모든 계산과 값 설정이 끝난 후, 모달창을 화면에 표시합니다.
	document.querySelector("#receiptModal").style.display = "flex";
}

// 모달창의 'X' 버튼을 클릭했을 때 모달을 닫는 함수입니다.
function closeModal() {
	document.querySelector("#receiptModal").style.display = "none";
}

// 모달창 바깥 영역을 클릭했을 때 모달을 닫는 이벤트 리스너입니다.
window.onclick = function(event) {
    const modal = document.getElementById('receiptModal');
    // 클릭된 요소가 모달창 자신일 경우에만 닫습니다.
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
