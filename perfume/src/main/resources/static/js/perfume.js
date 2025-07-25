window.onload = function() {
    const form = document.getElementById('perfumeForm');
    form.onsubmit = function(event) {
        const name = form.name.value.trim();
        const gender = form.gender.value;
        const age = form.age.value.trim();
		const favoriteScent = form.favoriteScent.value;
		const favoriteBrand = form.favoriteBrand.value;
        const usageFrequency = form.querySelector('input[name="usageFrequency"]:checked');
        const purchaseBudget = form.purchaseBudget.value;

        if (!name) {
            alert('이름을 입력해주세요.');
            form.name.focus();
            event.preventDefault();
            return false;
        }

        if (!gender) {
            alert('성별을 선택해주세요.');
            form.gender.focus();
            event.preventDefault();
            return false;
        }

        if (!age) {
            alert('나이를 입력해주세요.');
            form.age.focus();
            event.preventDefault();
            return false;
        } else if (isNaN(age) || age < 0 || age > 120) {
            alert('나이는 0에서 120 사이의 숫자여야 합니다.');
            form.age.focus();
            event.preventDefault();
            return false;
        }
		
        if (!favoriteScent) {
            alert('가장 좋아하는 향기를 선택하세요.');
            form.name.focus();
            event.preventDefault();
            return false;
        }
		
        if (!favoriteBrand) {
            alert('선호하는 향수 브랜드를 입력하세요.');
            form.name.focus();
            event.preventDefault();
            return false;
        }

        if (!usageFrequency) {
            alert('사용 빈도를 선택해주세요.');
            event.preventDefault();
            return false;
        }

        if (!purchaseBudget) {
            alert('구매 예산을 선택해주세요.');
            form.purchaseBudget.focus();
            event.preventDefault();
            return false;
        }

        event.submit();
    };
};
