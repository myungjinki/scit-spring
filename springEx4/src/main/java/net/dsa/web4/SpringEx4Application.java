package net.dsa.web4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
	JPA Auditing 기능을 활성화
	 - 이 설정이 있어야 @CreatedDate, @LastModifiedDate
	   같은 Annotation이 동작함
 */
@EnableJpaAuditing
@SpringBootApplication
public class SpringEx4Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringEx4Application.class, args);
	}

}
