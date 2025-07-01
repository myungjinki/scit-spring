package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("session")
public class SessionController {
	
	/*
		[ 세션 ]
		세션은 서버 측에서 사용자의 상태를 유지하는 메커니즘
		서버는 각 클라이언트에 대해 고유한 세션 ID를 생성하고,
		클라이언트가 해당 세션 ID를 통해 서버에 상태를 유지
		
		 - 저장 위치: 서버 측(서버 메모리나 DB 등)에 저장
		 - 유효 기간: 세션에는 만료 시간이 있으며, 일정 기간 활동이 없으면 세션 만료
		 - 보안: 서버 측에 저장되므로 비교적 안전
		 - 범위: 서버 내에서만 유효
	 */
	// http://localhost:9002/web2/;jsessionid=4FF608769044F902D1D567DC5BF69A8B
	// 이런 에러가 발생하지만, 세션 아이디를 URL을 통해 보내기 때문임.
	// 이걸 따로 처리할 수 있지만, 길기 때문에 우선은 귀찮더라도 한 번만 참자.
	@GetMapping("session1")
	public String session1(HttpSession session) {
		session.setAttribute("name", "abc");
		return "redirect:/";
	}
	
	// 세션 삭제
	@GetMapping("session2")
	public String session2(HttpSession session) {
		session.removeAttribute("name");
		return "redirect:/";
	}
	
	// 세션 읽기
	@GetMapping("session3")
	public String session3(HttpSession session) {
		String name = (String) session.getAttribute("name");
		log.debug("session 정보: {}", name);
		return "redirect:/";
	}
	
	// 로그인 페이지로 이동
	@GetMapping("login")
	public String login() {
		return "session/login";
	}
	
	// 로그인 처리
	@PostMapping("login")
	public String loginProcess(
			HttpSession session,
			@RequestParam("id") String id,
			@RequestParam("password") String pw
			) {
		log.debug("로그인폼에서 입력한 값: {}/{}", id, pw);
		if (id.equals("abc") && pw.equals("123")) {
			session.setAttribute("loginId", id);
			return "redirect:/";
		} else {
			log.debug("로그인 되지 않았습니다");
			return "session/login";
		}
	}
	
	// 로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		session.invalidate();	// 세션 완전 종료
		return "redirect:/";
	}
	
	// 로그인 확인 페이지
	@GetMapping("loginTest")
	public String loginTest(HttpSession session) {
		String id = (String) session.getAttribute("loginId");
		log.debug("id: {}", id);
		
		if (id == null || !id.equals("abc")) {
			log.debug("로그인 ID가 없습니다.");
			return "redirect:/";
		}
		return "session/loginTest";
	}
}
