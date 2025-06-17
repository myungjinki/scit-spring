package net.dsa.web.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary // 여러개의 구현체 중에 기본으로 사용할 객체 지정 // 우선순위 지정, Spring에게 알려주는 용도
@Service("TaxiServiceImpl") // 서비스에 이름 붙이기
public class TaxiServiceImpl implements TransportationService {
	
	@Override
	public void move() {
//		System.out.println("[ 콜 수신 ]");		// 부가 기능
		System.out.println("택시를 운행합니다.");	// 핵심 기능
//		System.out.println("[ 콜 처리 ]");		// 부가 기능
	}
}
