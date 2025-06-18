package net.dsa.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	// http://localhost:9991
	@GetMapping({"", "/"})
	public String Home() {
		return "home";		// home .html을 view Resolve가 src/main/resources/templates에서 찾는다.
	}
	
	// Thymeleaf 문법, 오타 90%
	// 오답노트 쓰는 작성을 들이자.
	// 프로젝트 발표 중 에러가 뜰 경우 -> 에러처리를 해놓으면 괜찮다.
	@GetMapping("exception500")
	public String exception500() {
		throw new RuntimeException();
	}
}