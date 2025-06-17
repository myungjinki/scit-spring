package net.dsa.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.dsa.web.service.TransportationService;

@Controller
public class TaxiController {
	
	@Autowired // <- DI, new 키워드를 사용하지 않아도 Spring boot가 알아서 인스턴스를 생성
	@Qualifier("DeluxeTaxiServiceImpl") // @Primary Service대신 서비스 이름을 직접 지정
	TransportationService ts; // <- Taxi, Deluxe를 코드로 일일히 바꾸지 않아도 되게끔 인터페이스 생성
	
	@GetMapping("/move")
	public String moveRequest() {
		
		ts.move();
		
		// http://localhost:9990/web0 를 처리할 수 있는 메서드 호출
		return "redirect:/";
	}
}