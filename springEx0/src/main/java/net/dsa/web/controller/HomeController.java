package net.dsa.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	// http://localhost:9990
	@GetMapping({"", "/"})
	public String Home() {
		return "home";		// home .html을 view Resolve가 src/main/resources/templates에서 찾는다.
	}
}
