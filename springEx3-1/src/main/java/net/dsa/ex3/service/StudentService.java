package net.dsa.ex3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex3.dto.StudentDTO;
import net.dsa.ex3.entity.StudentEntity;
import net.dsa.ex3.repository.StudentRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentRepository sr;
	
	public boolean save(StudentDTO student) {
		
		StudentEntity entity = new StudentEntity();
		StudentDTO.convertDTO_to_Entity(entity, student);
		StudentEntity savedEntity = sr.save(entity);
		log.debug("[service-save] studentEntity: {}", savedEntity);
		return false;
	}

	public List<StudentDTO> findAll() {
		
		List<StudentEntity> entityList = sr.findAll();
		List<StudentDTO> dtoList = new ArrayList<>();
		
		for (StudentEntity entity : entityList) {
			StudentDTO dto = new StudentDTO();
			StudentDTO.convertEntity_to_DTO(entity, dto);
			dtoList.add(dto);
		}
		
		log.debug("[service-findAll] studentEntity: {}", "");
		return dtoList;
	}

	public boolean deleteById(int sid) {
		boolean result = sr.existsById(sid);
		
		if (result) {
			sr.deleteById(sid);
			log.debug("[service-deleteById] Result: {}", "성공");
			return true;
		}
		
		log.debug("[service-deleteById] Result: {}", "실패");
		return false;
	}

	public StudentDTO findById(int sid) {
		StudentEntity entity = sr.findById(sid)
				.orElseThrow( () ->  // orElseThrow()라는 새로운 메서드
				new EntityNotFoundException("없는 ID") // 실패할경우 @Transactional 어노테이션이 동작해 롤백
			);
		StudentDTO student = new StudentDTO();
		StudentDTO.convertEntity_to_DTO(entity, student);
		log.debug("[service-findById] Result: {}", student);
		return student;
	}	
}