package net.dsa.ex2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StaticController {

	@GetMapping("js")
	public String js() {
		return "js";
	}
	
	@GetMapping("css")
	public String css() {
		return "css";
	}
}
