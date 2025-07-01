package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("storage")
public class StorageController {
	
	@GetMapping("save")
	public String save() {
		return "storage/save";
	}
	
	@GetMapping("read")
	public String read() {
		return "storage/read";
	}
	
	@GetMapping("delete")
	public String delete() {
		return "storage/delete";
	}
}
