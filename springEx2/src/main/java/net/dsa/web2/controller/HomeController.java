package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	@GetMapping
	public String home(
			HttpSession session,
			Model model
			) {
		String id = (String) session.getAttribute("id");
		
		model.addAttribute("id", id);
		return "home";
	}
}
