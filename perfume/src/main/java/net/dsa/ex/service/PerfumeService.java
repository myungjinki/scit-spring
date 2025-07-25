package net.dsa.ex.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex.dto.PerfumeDTO;
import net.dsa.ex.entity.PerfumeEntity;
import net.dsa.ex.repository.PerfumeRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PerfumeService {

	private final PerfumeRepository pr;
	
	public void save(PerfumeDTO perfume) {
		PerfumeEntity entity = perfume.toEntity();
		pr.save(entity);
	}

	public List<PerfumeDTO> findAll() {
		Sort sort = Sort.by(
				Sort.Order.desc("gender"),
				Sort.Order.asc("age"),
				Sort.Order.desc("completionTime")				
		);
		
		List<PerfumeEntity> perfumeEntity = pr.findAll(sort);
		List<PerfumeDTO> perfumeList = new LinkedList<>();
		
		for (PerfumeEntity entity : perfumeEntity) {
			PerfumeDTO perfume = PerfumeDTO.fromEntity(entity);
			perfumeList.add(perfume);
		}
		return perfumeList;
	}

	public int countByGender(String gender) {
		return pr.countByGender(gender);
	}
}
