package net.dsa.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping({"", "/"})
	public String home() {
		return "home";
	}
	@GetMapping("introduce")
	public String introduce() {
		return "introduce";
	}
	@GetMapping("notice")
	public String notice() {
		return "notice";
	}
	@GetMapping("inquiry")
	public String inquiry() {
		return "inquiry";
	}
}
