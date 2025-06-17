package net.dsa.web.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransportationAspect {
	
	/**
	 * move() 메서드 실행시 로그 출력
	 * @param joinPoint
	 */
	@Before("execution(* net.dsa.web.service.TransportationService.*(..))")
	public void logBefore(JoinPoint joiPoint) {
		System.out.println("> [ 콜 수신 ]");
	}
	@After("execution(* net.dsa.web.service.TransportationService.*(..))")
	public void logAfter(JoinPoint joiPoint) {
		System.out.println("> [ 결제 처리 ]");
	}
}
