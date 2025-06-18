package net.dsa.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {
	
	@GetMapping("image")
	public String image() {
		System.out.println("image 경로 요청시 메서드 실행");
		return "image";
	}
	
	@GetMapping("sub/image2")
	public String image2() {
		// template 패키지에서 image2.html을 찾아 가겠다
		return "image2";
	}
	
	@GetMapping("sub/image3")
	public String image3() {
		// template.sub 패키지에서 image3.html을 찾겠다
		return "sub/image3";
	}
	
	@GetMapping("css")
	public String css() {
		return "css";
	}
	
	@GetMapping("js")
	public String js() {
		return "js";
	}
}
