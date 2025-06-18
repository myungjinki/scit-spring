package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Person;

@Controller
@Slf4j
@RequestMapping("param")
public class ParamController {
	
	// = /param/view1
	@GetMapping("view1")
	public String view1() {
		return "param/view1";
	}
	
	@GetMapping("view2")
	public String view2() {
		return "param/view2";
	}
	
	@GetMapping("view3")
	public String view3() {
		return "param/view3";
	}
	
	@GetMapping("param1")
	public String param1(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "age") int age,
			@RequestParam(name = "phone") String phone
			) {
		// http://localhost:9992/web2/param/param1
		// 				?name=홍길동&age=99&phone=01012345678
		
		log.debug("param1 log = name: {}, age: {}, phone: {}"
					, name, age, phone);
		return "redirect:/";
	}
	
	@PostMapping("param2")
	public String param2(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "age") int age,
			@RequestParam(name = "phone") String phone
			) {
		
		log.debug("param2 log = name: {}, age: {}, phone: {}"
				, name, age, phone);
		
		return "redirect:/";
	}
	
	// Person DTO로 form 데이터를 처리할 수도 있다.
	@PostMapping("param3")
	public String param3(Person p) {
		log.debug("param3 log = p: {}", p);
		
		return "redirect:/";
	}
	
	@GetMapping("param4")
	public String param4(Person p) {
		log.debug("param4 log = p: {}", p);
		
		return "redirect:/";
	}
	
	@GetMapping("param5")
	public String param5(
			// defaultValue 파라미터가 들어오면 그 데이터 그대로 쓰되, 없으면 기본값
			@RequestParam(name = "name", defaultValue = "아무개") String name,
			@RequestParam(name = "age", defaultValue = "0") int age,
			@RequestParam(name = "phone", defaultValue = "01011112222") String phone
			) {
		log.debug("param5 log = name: {}, age: {}, phone: {}"
					, name, age, phone);
		
		return "redirect:/";
	}
	
	// import org.springframework.ui.Model; import 주의해야 함!
	// Model: 컨트롤러에서 뷰(JSP, Thymeleaf 등)로 데이터를 넘겨주는 데 사용되는 객체
	// Controller에서 HTML로 넘어가는 순간만 유효한 1회성 객체구조
	// * redirect:/ 등의 재요청에는 저장된 데이터가 없어짐 // 리다이렉트 관련 객체가 따로 또 있음
	// Model을 사용할 때는 redirect 주의해야 한다.
	@GetMapping("model")
	public String model(Model model) {
		String str = "문자열";
		int num = 100;
		Person p = new Person("홍길동", 33, "01022223333");
		
		model.addAttribute("str", str);
		model.addAttribute("num", num);
		model.addAttribute("person", p);
		
		return "param/model";
	}
}
