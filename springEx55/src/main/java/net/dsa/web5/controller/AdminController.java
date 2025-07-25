package net.dsa.web5.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.MemberDTO;
import net.dsa.web5.service.MemberService;

@Controller
@Slf4j
@RequestMapping("admin")
@RequiredArgsConstructor
// 해당 클래스의 모든 메서드 실행 전에 Spring Security가 hasRole('ADMIN') 조건 검사
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	private final MemberService ms;
	
	
	@GetMapping("page")
	public String admin() {
		return "admin/adminPage";
	}
	/*
	 * 회원목록을 조회
	 * @param Model
	 * @return memberList.html
	 */
	@GetMapping("list")
	public String list(Model model) {
		try {
			List<MemberDTO> memberList = ms.selectAll();
			model.addAttribute("memberList", memberList);
			log.debug("회원목록: {}", memberList);
 		} catch (Exception e) {
			log.debug("예외발생 조회실패");
		}
		return "admin/listForm";
	}	
	/*
	 * 권한 변경
	 * @param id 권한을 변경하고자 하는 아이디
	 * @return 회원 목록
	 */
	@GetMapping("update")
	public String update(@RequestParam("id") String memberId) {
		
		//권한을 변경
		try {
			ms.upgrade(memberId);
			log.debug("성공");
		} catch (Exception e) {
			log.debug("실패");
		}
		return "redirect:/admin/ListForm";
	}
	/*
	 * 계정상태 변경
	 * @param id
	 * @param enabled
	 * @return 회원목록
	 */
	@GetMapping("enabled")
	public String enable(
			@RequestParam("id") String memberId,
			@RequestParam("enabled") boolean enabled) {
		
		try {
			ms.updateEnabled(memberId, !enabled);
			log.debug("계정 변경 {} => {}", memberId, enabled);
		} catch (Exception e) {
			log.debug("실패입니다.");
		}
		
		return "redirect:list";
	}
	
}
