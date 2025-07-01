package net.dsa.web3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.service.MemberService;

@Controller
@Slf4j
@RequestMapping("basic")
// final로 선언된 모든 멤버변수를 초기화 하는 생성자를 자동으로 생성
@RequiredArgsConstructor
public class BasicController {
	
	// 1. 필드 주입
//	@Autowired
//	MemberService ms;
	
	// 2. 생성자 주입
//	private MemberService ms;
//	@Autowired
//	public BasicController(MemberService ms) {
//		this.ms = ms;
//	}
	
	// 3. 명시적인 생성자 사용
	// lombok은 final이 붙은 필드를 대상으로 자동으로 생성자 매개변수 만듦
	private final MemberService ms;
	
	/*
	 * 저장 테스트
	 * @return 메인화면으로 이동
	 */
	@GetMapping("insertData")
	public String insertData() {
		log.debug("[controller-insert] insertData 접근");
		
		ms.insertData();
		
		return "redirect:/";
	}
}
