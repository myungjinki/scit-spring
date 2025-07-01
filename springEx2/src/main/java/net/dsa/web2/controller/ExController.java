package net.dsa.web2.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Student;

/*
	ex/info
	ExController 생성
	template.ex 패키지 생성 후 info.html, infoOutput.html 생성
	1. 입력페이지에서 이름, 주민번호를 입력받아 서버로 전송
	2. 입력받은 데이터를 요청처리메서드에서 받아 주민번호로부터 정보 획득
	3. 이름, 나이, 생년월일, 성별 을 각각 Model 객체에 담기
	4. 출력페이지에서 해당 정보 출력
 */

/*
	ex/count
	count.html 에서 출력
	
	1. home.html 요청 > 요청을 처리하는 메서드 생성
	2. 메서드에서 쿠키를 읽어오기
	3. 카운트 증가
	4. 쿠키 생성
	5. response에 쿠키 저장
	6. count.html 이동 및 출력
 */

@Controller
@Slf4j
public class ExController {
	
	@GetMapping("ex/info")
	public String info() {
		log.debug("ex/info 경로를 처리하는 메서드 실행");
		return "ex/info";
	}
	
	@GetMapping("ex/infoOutput")
	public String infoOutput(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "ssn") String ssn,
			Model model
			) {
		log.debug("param = name: {}, snn: {}", name, ssn);
		
		char gender = ssn.charAt(7);
		String genderResult = (gender == '1' || gender == '3') ? "남자" : "여자";
		
		int year = Integer.parseInt(ssn.substring(0, 2));
		int month = Integer.parseInt(ssn.substring(2, 4));
		int day = Integer.parseInt(ssn.substring(4, 6));
		
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		
		int age;
		if (gender == '1' || gender == '2')
			age = y - year - 1900;
		else
			age = y - year - 2000;
		
		String birth = year + "년 " + month + "월 " + day + "일";
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		model.addAttribute("birth", birth);
		model.addAttribute("gender", genderResult);
		
		return "ex/infoOutput";
	}
	
	@GetMapping("ex/count")
	public String count(
			@CookieValue(name = "count", defaultValue = "0") int count,
			Model model,
			HttpServletResponse response
			) {
		
		count++;
		model.addAttribute("count", count);
		
		Cookie c = new Cookie("count", Integer.toString(count));
		c.setMaxAge(24*60* 60);
		response.addCookie(c);
		
		return "ex/count";
	}
	
	@GetMapping("ex/darkmode")
	public String darkmode() {
		return "ex/darkmode";
	}
	
	@GetMapping("ex/temp")
	public String temp() {
		return "ex/temp";
	}
	
	@GetMapping("ex/thymeleaf")
	public String thymeleaf(Model model) {
		
		// Data 생성 및 Model에 저장
		
		List<Student> list = List.of(
				new Student("홍길동", 20, "010-1111-2222", 30),
				new Student("김철수", 21, "010-3333-4444", 90),
				new Student("이영희", 22, "010-5555-6666", 65)				
		);
		model.addAttribute("studentList", list);	
		
		return "ex/print";
	}
}
