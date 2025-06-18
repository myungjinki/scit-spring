package net.dsa.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {
	@GetMapping("image")
	public String image() {
		return "image";
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
