package net.dsa.ex3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex3.dto.StudentDTO;
import net.dsa.ex3.service.StudentService;

@Controller
@Slf4j
@RequestMapping("student")
@RequiredArgsConstructor
public class StudentController {
	
	private final StudentService ss;

	@GetMapping("new")
	public String enrollForm() {
		log.debug("[controller-enrollForm] 렌더링 성공");
		return "enroll";
	}
	
	@PostMapping("new")
	public String enroll(StudentDTO student) {		
		boolean isSaved = ss.save(student);
		log.debug("[controller-enroll] 학생등록 성공");
		return "redirect:/";
	}
	
	@GetMapping("update")
	public String updateForm(
			@RequestParam(name = "sid") int sid,
			Model model
		) {
		try {
			StudentDTO student = ss.findById(sid);
			log.debug("[controller-update] 학생조회 성공: {}", student);	
			model.addAttribute("student", student);
			return "updateForm";
		} catch (Exception e){
			log.debug("[controller-update] 학생조회 실패: {}", e);			
			return "redirect:/";
		}		
	}
	
	@PostMapping("update")
	public String update(StudentDTO student) {
		ss.save(student);
		log.debug("[controller-update] 학생수정 성공: {}", student);		
		return "redirect:/";
	}
	
	@GetMapping("select")
	public String select(
				@RequestParam(name = "sid") int sid,
				Model model
			) {
		try {
			StudentDTO student = ss.findById(sid);
			log.debug("[controller-update] 학생조회 성공: {}", student);	
			model.addAttribute("student", student);
			return "select";
		} catch (Exception e){
			log.debug("[controller-update] 학생조회 실패: {}", e);			
			return "redirect:/";
		}
	}
	
	@GetMapping("delete")
	public String delete(@RequestParam(name = "sid") int sid) {
		log.debug("삭제할 id: {}", sid);
		ss.deleteById(sid);
		return "redirect:/";
	}
}
