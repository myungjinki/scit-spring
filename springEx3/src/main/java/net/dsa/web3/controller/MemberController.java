package net.dsa.web3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		// URL 직접 입력하는 경우
		if (id != null) {
			MemberDTO member = ms.selectData(id);
			log.debug("현재 접속 중인 유저의 회원정보: {}", member);
			model.addAttribute("member", member);			
		}
		
		return "member/updateForm";
	}
	
	/**
	 * 회원정보 수정 처리
	 * @param MemberDTO
	 * @return 메인페이지이동
	 */
	@PostMapping("update")
	public String update(MemberDTO member) {
		ms.save(member);
		return "redirect:/";
	}
	
	/**
	 *  회원정보 조회
	 *  @return ID 입력 페이지
	 */
	@GetMapping("select")
	public String select() {
		return "member/selectForm";
	}
	
	/**
	 * 검색폼에서 입력한 아이디를 전달받아 회원정보 조회
	 * @param id	조회할 아이디
	 * @param model
	 * @return select.html
	 */
	@PostMapping("select")
	public String select2(
			@RequestParam("id") String id,
			Model model
			) {
		
		if (id != null) {
			MemberDTO member = ms.selectData(id);			
			log.debug("회원조회: {}", member);
			model.addAttribute("searchId", id); // 못찾을 때 사용
			model.addAttribute("member", member); // 찾으면 member 못찾으면 null
		}
		
		return "member/select";
	}
	
	/**
	 * URL 로부터 들어온 요청을 처리하는 메서드
	 * http://localhost:9993/web/member/info/abc
	 * @param id 조회할 아이디
	 * @param model
	 * @return select.html
	 */
	@GetMapping({"info" + "/{id}", "info"})
	public String info(
			@PathVariable(name = "id", required = false) String id, // required = false라면 없을때 null
			Model model
		) {
		/*
			@PathVariable
			URL 경로 자체에 포함된 값을 파라미터로 받아오는 방식
				@RequestParam	/member?id=abc&pw=123	쿼리스트링
				@PathVariable	/member/abc				경로 변수
		 */
		
		log.debug("경로 변수: {}", id);
		if (id != null) {
			MemberDTO member = ms.selectData(id);			
			log.debug("회원조회: {}", member);
			model.addAttribute("searchId", id); // 못찾을 때 사용
			model.addAttribute("member", member); // 찾으면 member 못찾으면 null
		}
		return "member/select";
	}
	
	/**
	 * 회원목록 조회
	 * @param Model
	 * @return list.html
	 */
	@GetMapping("list")
	public String list(Model model) {
		
		// 회원목록 전체 조회
		List<MemberDTO>	memberList = ms.selectAllData();
		log.debug("회원정보 전체조회: {}", memberList);
		
		model.addAttribute("memberList", memberList);
		
		return "member/list";
	}
	
	/**
	 * 회원정보 삭제 처리
	 * @param id	삭제할 id
	 * @return list.html
	 */
	@GetMapping("delete")
	public String delete(@RequestParam(name = "id") String id) {
		log.debug("삭제할 id: {}", id);
		
		ms.deleteData(id);
		
		// redirect를 하는 이유는 /member/list 컨트롤러의 로직을 다시 실행하기 위함
		return "redirect:/member/list";
	}
}