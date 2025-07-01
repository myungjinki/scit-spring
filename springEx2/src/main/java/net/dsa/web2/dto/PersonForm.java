package net.dsa.web2.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
	검증 Annotation
	@Size : 문자열의 길이
	@NotNull : null 불가
	@NotEmpty : null, "" 불가
	@NotBlank : null, "", " " 불가
	@Pattern : 정규식 사용
	@Max : 최대값
	@Min : 최소값
	...
 */

@Getter @Setter @ToString
public class PersonForm {
	
	@Pattern(regexp = "^[가-힣]+$", message = "한글만 입력 가능합니다")
	@NotBlank
	String name;
	
	@Min(value = 1, message = "나이는 1 이상이어야 합니다")
	@Max(value = 200, message = "나이는 200 이하이어야 합니다")
	int age;
	
	
	@Pattern(regexp = "^010\\d{4}\\d{4}$"
			, message = "전화번호 형식은 01011112222 형식입니다")
	String phone;
}
