package net.dsa.ex2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex2.dto.Member;
import net.dsa.ex2.service.MemberService;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	MemberService ms;
	
	/**
	 * 회원가입 처리
	 */
	@GetMapping("join")
	public String join() {
		return "member/join";
	}
	@PostMapping("join")
	public String joinForm(Member member) {
		log.debug("> 회원가입 데이터: {}", member);
		
		boolean result = ms.save(member);
		
		if (result) {
			log.debug("> 회원가입 성공!");
			return "redirect:/";
		} else {
			log.debug("> 이미 존재하는 ID입니다.");
			return "member/join";
		}
	}
	/**
	 * 로그인, 로그아웃 처리
	 */
	@GetMapping("login")
	public String login(
				@CookieValue(name = "recentId", defaultValue = "") String recentId,
				Model model
			) {
		log.debug("> 최근 ID: {}", recentId);
		model.addAttribute("recentId", recentId);
		
		return "member/loginForm";
	}
	@PostMapping("login")
	public String loginForm(
				HttpSession session,
				@RequestParam("id") String id,
				@RequestParam("pw") String pw,
				@RequestParam(name = "check", defaultValue = "false") boolean check,
				HttpServletResponse response
			) {
		log.debug("> 로그인 데이터: id={}, pw={}, check={}", id, pw, check);
		
		// Ctrl + 1하면 자동으로 메서드 만들어짐
		boolean result = ms.loginCheck(id, pw);
		
		if (result) {
			session.setAttribute("loginId", id);
			String saveId = (String) session.getAttribute("loginId");
			log.debug("> 로그인 성공! 현재 세션ID: {}", saveId);
			
			if (check) {
				Cookie c = new Cookie("recentId", id);
				c.setMaxAge(24*60*60*3);
				response.addCookie(c);
				log.debug("> 쿠키 저장");
			} else {
				// 쿠키를 삭제하기 위한 요청경로
				return "redirect:/member/remove";
			}
			
		} else {
			log.debug("> 로그인 실패..");
			// 로그인 페이지에서 쿠키값을 사용하기 위한 요청경로
			// 꼭 이 방법을 기억해보자!! 아래처럼 리다이렉트를 사용하지 않으면
			// @GetMapping("login")의 로직을 또 추가해야 해서 중복이다.
			return "redirect:/member/login"; // 왜 redirect를 사용해야 할까? 쿠키값 때문에 그렇다
		}
		
		return "redirect:/";
	}
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		session.invalidate();
		log.debug("> 로그 아웃!");
		return "redirect:/";
	}
	
	/**
	 * 쿠키 삭제
	 */
	@GetMapping("remove")
	public String remove(HttpServletResponse response) {
		Cookie c1 = new Cookie("recentId", null);
		c1.setMaxAge(0);
		response.addCookie(c1);
		log.debug("> 쿠키 삭제 성공!");
		return "redirect:/";
	}
	
	/**
	 * 회원목록 처리
	 */
	@GetMapping("list")
	public String memberList(Model model) {
		List<Member> list = ms.selectList();
		model.addAttribute("memberList", list);
		log.debug("> 회원목록: {}", list);
		return "member/list";
	}
}
