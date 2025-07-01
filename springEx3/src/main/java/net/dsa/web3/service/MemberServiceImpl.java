package net.dsa.web3.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.entity.MemberEntity;
import net.dsa.web3.repository.MemberRepository;

/**
 * @Transactional
 *   - 메서드나 클래스에 적용하면, 해당 메서드가 호출될 때 트랜잭션 시작.
 *   - 메서드가 정상적으로 완료되면 트랜잭션 커밋(commit)
 *   - 예외가 발생하면 트랜잭션 롤백(rollback)
 *   - 해당 어노테이션은 public 메서드에만 적용됨
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository mr;

	/**
	 * 회원정보 생성 후 DB에 저장
	 */
	@Override
	public void insertData() {
		// 일반적인 인스턴스 생성
		MemberEntity m1 = new MemberEntity("eeg", "555", "장영실"
				, "010-1111-2222", "서울특별시 금천구");
		
		// builder 사용
		MemberEntity m2 = MemberEntity.builder()
						 .id("bbb")
						 .pw("123")
						 .name("고길동")
						 .phone("010-2222-3333")
						 .address("서울특별시 강남구")
						 .build();
		
		mr.save(m1);
		log.debug("[service-save] memberEntity: {}", m1);
		log.debug("[service-save] memberEntity2: {}", m2);
	}
	
	
}
