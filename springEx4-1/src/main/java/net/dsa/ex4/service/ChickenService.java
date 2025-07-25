package net.dsa.ex4.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex4.dto.ChickenDTO;
import net.dsa.ex4.entity.ChickenEntity;
import net.dsa.ex4.repository.ChickenRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChickenService {
	private final ChickenRepository cs;

	public void save(ChickenDTO chicken) {
		ChickenEntity entity = new ChickenEntity();
		ChickenDTO.ChickenDTO_toEntity(chicken, entity);
		ChickenEntity newEntity = cs.save(entity);
		log.debug("> ChickenService: Saved Chicken: {}", newEntity);
	}
	
	
}
