package net.dsa.ex3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex3.dto.StudentDTO;
import net.dsa.ex3.service.StudentService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
	
	private final StudentService ss;

	@GetMapping({"", "/"})
	public String home(Model model) {
		
		List<StudentDTO> studentList =  ss.findAll();
		model.addAttribute("studentList", studentList);
		
		log.debug("[controller-home] studentEntity: {}", studentList);
		return "home";
	}
}
