package net.dsa.web4.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web4.dto.GuestBookDTO;
import net.dsa.web4.service.GuestService;

@Controller
@Slf4j
@RequestMapping("guest")
@RequiredArgsConstructor
public class GuestController {

	private final GuestService service;
	
	/**
	 * 글 입력 폼으로 이동
	 * @return writeForm.html
	 */
	@GetMapping("write")
	public String writeForm() {
		return "writeForm";
	}
	
	/**
	 * 작성한 글 전달받아 저장
	 * @param 입력받은 작성글
	 * @return 방명록페이지로 가도록
	 */
	@PostMapping("write")
	public String write(GuestBookDTO guestbook) {
		log.debug("param: {}", guestbook);
		service.write(guestbook);
		return "redirect:/";
	}
	
	/**
	 * 글 목록 보기
	 * @param Model
	 * @return guestList.html
	 */
	@GetMapping("guestList")
	public String guestList(Model model) {
		
		List<GuestBookDTO> guestList = service.selectAll();
		model.addAttribute("guestList", guestList);
		log.debug("글 목록: {}", guestList);
		
		return "guestList";
	}
	
	/**
	 * 글 삭제 처리
	 * @param num	삭제할 글번호
	 * @param password 입력한 비밀번호
	 * @param RedirectAttributes 
	 * 			redirect 할 때 오류 메시지를 전달할 객체
	 * 
	 */
	@PostMapping("delete")
	public String delete(
			GuestBookDTO guestbook, 
			RedirectAttributes ra) {
		
		try {
			service.delete(guestbook);
		} catch (Exception e) {
			ra.addFlashAttribute("msg", "삭제 실패했습니다.");
		}
		
		return "redirect:/guest/guestList";
	}
	
	
	
	
	
}
