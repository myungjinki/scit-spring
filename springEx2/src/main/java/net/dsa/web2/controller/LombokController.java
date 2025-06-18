package net.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import net.dsa.web2.dto.Person;

@Controller
@Slf4j // Simple Logging Facade for Java
public class LombokController {
	
	@GetMapping("lombok/lombok1")
	public String lombok1() {
		
		Person p = new Person();
		p.setName("홍길동");
		p.setAge(99);
		p.setPhone("01012345678");
		
		System.out.println(p);
		
		// Lombok 방식
		// Method chaining 방식으로 builder pattern을 사용해서도 생성 가능
		Person ps = Person.builder()
					.name("홍길동")
					.age(99)
					.phone("01022223333")
					.build();
		
		return "lombok/lombok1";
	}
	
	// Slf4j를 사용해
	// 로그의 레벨을 지정할 수 있다.
	// 이제는 sysout을 사용하지 않고 Slf4j를 사용해 로그를 기록
	// 로그를 사용해 어느 단계까지 되는지, 개발이 성공적으로 되었는지 로그를 사용해 확인한다.
	// 로그를 잘 사용해야 한다.
	@GetMapping("lombok/lombok2")
	public String lombok2() {
		
		log.error("error");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");
		
		String str = "로그로그";
		log.debug("확인하고자 하는 데이터: {}", str);
		return "lombok/lombok2";
	}
}
