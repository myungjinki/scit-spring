package net.dsa.web5.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@GetMapping({"","/"})
	public String home() {
		return "home";
	}
	
	@GetMapping("thymeleaf")
	public String thymeleaf(
			@AuthenticationPrincipal UserDetails user) {
		log.debug("Authentication 객체 정보 출력:");
		log.debug(user.getUsername());
		log.debug(user.getPassword());
		log.debug("" + user.getAuthorities());
		log.debug("" + user.isAccountNonExpired());
		log.debug("" + user.isAccountNonLocked());
		log.debug("" + user.isCredentialsNonExpired());
		log.debug("" + user.isEnabled());
		return "thymeleaf";
	}
	
	// WebSecurityConfig 설정에서 예외핸들러를 통한 예외발생 처리 메서드
	@GetMapping("/error/403")
	public String error403() {
		log.debug("error 403 - 권한이 없습니다.");
		return "error/403";
	}
	
	// application.properties에서 설정한 Value
	@Value("${board.uploadPath}")
	String uploadPath;
	
	final String path = "c/upload";	
	
	// File 클래스
	@GetMapping("file")
	public String file() {
		/*
		 	IOStream?
		 	 - 자바의 IOStream(Input/Output Stream)은
		 	   데이터의 읽고 쓰기 위한 통로 or 흐름
		 	 - 파일, 키보드 모니터, 네트워크 메모리 등
		 	File 클래스?
		 	 - 파일이나 폴더의 존재 여부 확인, 생성, 삭제, 이름변경
		 	   , 경로 정보 확인 등을 할 때 사용하는 클래스
		 	 - 파일 내용을 읽거나 쓰는 역할이 아닌, 파일 자체를 관리
		 	 
		 	 exists()			파일이나 폴더가 존재하는지 확인
		 	 isFile()			이 경로가 파일인지 확인
		 	 isDirectory()		이 경로가 폴더인지 확인
		 	 mkdir()			폴더 생성
		 	 createNewFile()	빈 파일 생성
		 	 delete()			파일이나 폴더 삭제
		 	 getName()			파일 또는 폴더 이름 반환
		 	 getPath(), getAbsolutePath()	경로 문자열 반환
		 */
		
		// 1. 파일 객체 생성
		File file = new File(uploadPath + "/" + "example.txt");
		File dir  = new File(uploadPath + "/" + "myfolder"); 
		// 2. 파일이 존재하지 않으면 생성
		try {
			if (!file.exists()) {
				boolean created = file.createNewFile();
				log.debug("파일 생성 여부: {}", created);
			} else {
				log.debug("파일이 이미 존재합니다");			
			}
			
		// 3. 디렉토리(폴더) 생성
			if (!dir.exists()) {
				boolean dirCreated = dir.mkdir();
				log.debug("폴더 생성 여부: {}", dirCreated);
			} else {
				log.debug("폴더가 이미 존재합니다");
			}
		// 4. 파일 이름과 경로 출력
			log.debug("파일 이름: " + file.getName());
			log.debug("파일 경로: " + file.getAbsolutePath());
			
		// 5. 파일 삭제(필요시)
			boolean deleted = file.delete();
			log.debug("파일 삭제 여부: {}", deleted);
			deleted = dir.delete();
			log.debug("파일 삭제 여부: {}", deleted);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
}
