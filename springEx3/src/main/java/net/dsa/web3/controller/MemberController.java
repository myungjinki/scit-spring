package net.dsa.web3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.MemberDTO;
import net.dsa.web3.service.MemberService;

@Slf4j
@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService ms;
	
	/**
	 * 회원가입 폼으로 이동
	 * @return 회원가입 html 경로
	 */
	@GetMapping("join")
	public String join() {
		return "member/join";
	}
	
	/**
	 * 회원가입 처리
	 * @param member 회원가입정보
	 * @return 메인페이지 이동
	 */
	@PostMapping("join")
	public String join(MemberDTO member) {
		log.debug("Form Data 확인: {}", member);
		
		// DB에 회원가입처리
		ms.save(member);
		return "redirect:/";
	}
	
	/**
	 * 로그인 페이지로 이동
	 * @return loginForm.html
	 */
	@GetMapping("login")
	public String loginForm() {
		return "member/loginForm";
	}
	
	/**
	 * 로그인 처리
	 * @param id	로그인페이지에서 입력한 아이디
	 * @param pw	로그인페이지에서 입력한 패스워드
	 * @param HttpSession	로그인 유저의 아이디를 저장할 세션 객체
	 * @return 메인페이지 or 로그인페이지
	 */
	@PostMapping("login")
	public String login(
			@RequestParam(name = "id") String id,
			@RequestParam(name = "pw") String pw,
			HttpSession session
			) {
		log.debug("MemberController-param : id={} pw={}", id, pw);
		
		/*
		 * 받아온 파라미터 id, pw 를 DB의 회원정보 중 일치한다면
		 * 세션객체에 id를 저장하고 메인페이지로 이동
		 * DB에 일치하는 회원정보가 없다면
		 * 로그인 페이지로 이동
		 */
		try {			
			boolean result = ms.loginCheck(id, pw);
			
			if (result) {
				session.setAttribute("loginId", id);
				String resultId = (String) session.getAttribute("loginId");
				log.debug("로그인 성공! 현재 세션정보: {}", resultId);
				return "redirect:/";
			} else {
				log.debug("로그인 실패! 비밀번호 불일치");
				return "member/loginForm";
			}
		} catch (Exception e) {
			log.debug("[예외 발생] 로그인 실패! DB에 저장된 정보가 없음");
			return "member/loginForm";
		}
	}
	
	/**
	 * 로그아웃 처리
	 * @param HttpSession
	 * @return 메인 페이지로 이동
	 */
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		String sessionId = (String) session.getAttribute("loginId");
		log.debug("로그아웃, 현재 세션 정보: {}", sessionId);
		session.invalidate();
		return "redirect:/";
	}
	
	/**
	 * 회원정보 수정페이지로 이동
	 * @param HttpSession 현재 접속중인 유저의 id를 읽기 위해서 사용
	 * @return updateForm 페이지 이동
	 */
	@GetMapping("update")
	public String updateForm(HttpSession session,
							Model model) {
		
		String id = (String) session.getAttribute("loginId");
		MemberDTO member = ms.selectData(id);
		log.debug("현재 접속 중인 유저의 회원정보: {}", member);
		model.addAttribute("member", member);
		
		
		return "member/updateForm";
	}
}