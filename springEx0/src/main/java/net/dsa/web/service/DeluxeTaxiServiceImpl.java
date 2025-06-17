package net.dsa.web.service;

import org.springframework.stereotype.Service;

@Service("DeluxeTaxiServiceImpl")
public class DeluxeTaxiServiceImpl implements TransportationService {
	
	@Override
	public void move() {
//		System.out.println("[ 콜 수신 ]");			// 부가 기능
		System.out.println("모범택시를 운행합니다.");		// 핵심 기능
//		System.out.println("[ 콜 처리 ]");			// 부가 기능
	}
}
