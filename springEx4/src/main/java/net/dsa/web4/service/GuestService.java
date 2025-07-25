package net.dsa.web4.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web4.dto.GuestBookDTO;
import net.dsa.web4.entity.GuestBookEntity;
import net.dsa.web4.repository.GuestRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GuestService {

	private final GuestRepository repository;

	public void write(GuestBookDTO guestbook) {
		GuestBookEntity entity = GuestBookEntity.builder()
								 .name(guestbook.getName())
								 .password(guestbook.getPassword())
								 .message(guestbook.getMessage())
								 .build();
		repository.save(entity);
	}

	/**
	 * 글 목록 조회
	 * @return	글 목록
	 */
	public List<GuestBookDTO> selectAll() {
		
		/*
			Spring Data JPA - Sort 객체
			데이터 조회 시 정렬(Order by) 을 손쉽게 적용할 수 있도록
			해주는 유틸리티 클래스.
			Sort 클래스는 정렬 기준을 지정하고, 이를 기반으로 쿼리를 수행할 때
			결과를 정렬할 수 있음.
			
			enum
			미리 정의된 고정된 값들의 목록을 나타내는 자료형
			클래스처럼 사용 가능, 하지만 상수들의 집합
		 */
		// 정렬조건이 한 개 일때
		Sort sort = Sort.by(Sort.Direction.DESC, "num");
		
		// 정렬조건이 여러 개 일때
		Sort sort2 = Sort.by(
			Sort.Order.desc("inputdate"),
			Sort.Order.desc("num"),
			Sort.Order.asc("name")
		);
		
		List<GuestBookEntity> entityList = repository.findAll(sort);
		List<GuestBookDTO>    dtoList    = new ArrayList<>();
		
		for (GuestBookEntity entity : entityList) {
			GuestBookDTO dto = GuestBookDTO.builder()
							   .num(entity.getNum())
							   .name(entity.getName())
							   .message(entity.getMessage())
							   .inputdate(entity.getInputdate())
							   .build();
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	/**
	 * 글 번호와 비밀번호를 전달받아 비밀번호가 일치하면 글 삭제
	 * @param guestbook
	 */
	public void delete(GuestBookDTO guestbook) {

		// 전달된 글 번호로 글 정보 조회
		GuestBookEntity entity = 
				repository.findById(guestbook.getNum())
				.orElseThrow(() 
					-> new EntityNotFoundException("해당 글이 없습니다."));
		
		// 글 번호와 일치하는 글 정보가 있으면 비밀번호 비교
		// 일치하지 않으면 예외를 발생시켜 rollback 처리
		if (!guestbook.getPassword().equals(entity.getPassword())) {
			throw new RuntimeException("비밀번호가 틀립니다.");
		} else {	// 일치하면 삭제
			repository.deleteById(entity.getNum());
		}
	}
	
}
