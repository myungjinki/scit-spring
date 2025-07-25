package net.dsa.web5.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.MemberDTO;
import net.dsa.web5.security.AuthenticatedUser;
import net.dsa.web5.service.MemberService;

@Controller
@Slf4j
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService ms;
	
	/**
	 * 회원가입 페이지로 이동
	 * @return joinForm.html
	 */
	@GetMapping("join")
	public String joinForm() {
		return "member/joinForm";
		
	}
	/*
	 * 회원가입 페이지에서 "ID 중복확인" 버튼을 클릭하면
	 * 새창으로 보여줄 검색 페이지로 이동
	 * @return idCheck.html
	 */
	@GetMapping("idCheck")
	public String idCheck() {
		return "member/idCheck";
	}
	/*
	 * 회원가입 정보를 받아 회원가입 처리
	 * @param memberDTO
	 * @return home.html
	 */
	@PostMapping("join")
	public String join(MemberDTO member) {
		log.debug("회원정보: {}", member);
		
		try {
			ms.join(member);
			log.debug("성공");
		}catch (Exception e) {
			log.debug("실패");
		}
		return "redirect:/";
	}
	/*
	 * ID 중복확인 페이지에서 검색 요청 처리
	 * @param searchId 검색할 아이디
	 * @return idCheck.html
	 */
	@PostMapping("idCheck")
	public String idCheck(
			@RequestParam("searchId") String searchId,
			Model model) {
		log.debug("searchId: {}", searchId);
		
		// 검색할 아이디를 서비스에서 사용 가능한지 조회(true면 사용 가능)
		boolean result = ms.idCheck(searchId);
		
		// 검색할 아이디와 결과를 저장
		model.addAttribute("searchId", searchId);
		model.addAttribute("result", result);
		
		return "member/idCheck";
	}
	/*
	 * 로그인 페이지로 이동
	 * @return logingForm.html
	 */
	@GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	/*
	 * 개인정보수정 폼으로 이동
	 * @param user 로그인한 사용자의 정보
	 * @param Model
	 * return updateForm.html
	 */
	@GetMapping("update")
	public String updateForm(
			@AuthenticationPrincipal AuthenticatedUser user, Model model) {
		
		try {			
			// 서비스로부터 아이디에 일치하는 회원의 정보를 조회
			MemberDTO memberDTO = ms.getMember(user.getUsername());
			model.addAttribute("member", memberDTO);
			log.debug("회원정보: {}", memberDTO);
		} catch (Exception e) {
			log.debug("회원정보 조회 실패");
		}
		return "member/updateForm";
	}
	/*
	 * 개인정보수정 폼에서 전달된 값 차리
	 * @param user
	 * @param memberDTO
	 * @return 메인화면
	 */
	@PostMapping("update")
	public String update(
			@AuthenticationPrincipal UserDetails user
			, MemberDTO memberDTO) {
		
		log.debug("수정폼에서 전달 된 값 {} :", memberDTO);
		memberDTO.setMemberId(user.getUsername());
		try {
			ms.update(memberDTO);
			log.debug("수정완료");
		} catch (Exception e) {
			log.debug("수정실패");
		}
		return "redirect:/";
	}
	
}
