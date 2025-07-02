package net.dsa.web3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web3.dto.MemberDTO;
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

	/*
	 *  JpaRepository 기본 제공 메서드
	 *  save(entity)		Insert or Update
	 *  findById(id)		ID 기준 조회
	 *  findAll()			전체 조회
	 *  delete(entity)		엔티티 삭제
	 *  deleteById(id)		ID 기준 삭제
	 *  count()				전체 개수 조회
	 */
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
	
	/**
	 * 회원정보 조회
	 * @param id 조회할 아이디
	 * @return 조회 결과를 담은 객체
	 */
	@Override
	public MemberDTO selectData(String id) {
		
		// 해당 ID로 회원정보 조회
		// 이렇게 Null 처리하면 문제가 발생할 가능성이 있음
		MemberEntity member = mr.findById(id).orElse(null);
		if (member == null)
			return null;
		
		log.debug("[service-find] memberEntity: {}", member);

		// MemberDTO
		MemberDTO memberDTO = MemberDTO.builder()
							  .id(member.getId())
							  .pw(member.getPw())
							  .name(member.getName())
							  .phone(member.getPhone())
							  .address(member.getAddress())
							  .build();
		return memberDTO;
	}

	/**
	 * 회원정보 수정
	 * @param MemberDTO 회원정보를 담은 객체
	 */
	@Override
	public void updateData(MemberDTO m) {
		
		/*
			Optional<T>
			null 값으로 인한 NullPointerException을 방지하기
			위한 자바 클래스
			 - null을 직접 쓰는 것보다 안정적이고 가독성이 높음
			findById는 Optional을 return하는데 그 이유는 null을 사용하지 않도록 유도하기 위함
		 */
		MemberEntity entity = mr.findById(m.getId())
				.orElseThrow( () ->  // orElseThrow()라는 새로운 메서드
					new EntityNotFoundException("없는 ID") // 실패할경우 @Transactional 어노테이션이 동작해 롤백
				);
		
		// MemberDTO의 수정할 정보를 entity에 세팅
//		entity.setId(m.getId());
		entity.setPw(m.getPw());
		entity.setName(m.getName());
		entity.setPhone(m.getPhone());
		entity.setAddress(m.getAddress());
		
		// 저장
		mr.save(entity);
	}

	/**
	 * 회원정보 삭제
	 * @param id 삭제할 아이디
	 * @return 삭제 여부 true / false
	 */
	@Override
	public boolean deleteData(String id) {
		
		// id에 일치하는 회원정보가 있는지 없는지를 true / false 리턴
		boolean result = mr.existsById(id);
		
		if (result) {
			mr.deleteById(id);
		}
		
		return result;
	}

	/**
	 * 모든 회원 정보 조회
	 * @return 모든 정보를 담은 리스트
	 */
	@Override
	public List<MemberDTO> selectAllData() {
		
		List<MemberEntity> entityList = mr.findAll();
		List<MemberDTO> dtoList = new ArrayList<>();
		
		for (MemberEntity entity : entityList) {
			MemberDTO dto = new MemberDTO();
//			dto.setId(entity.getId());
			MemberDTO.convertEntity_to_DTO(entity, dto);
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	/**
	 * 회원가입 처리
	 * @param MemberDTO 회원가입폼으로부터 입력받은 회원정보
	 */
	@Override
	public void save(MemberDTO member) {
		MemberEntity entity = new MemberEntity();
		MemberDTO.convertDTO_to_Entity(member, entity);
		MemberEntity savedEntity = mr.save(entity);
		log.debug("[service-form-save] memberEntity: {}", savedEntity);
	}

	/**
	 * 회원정보 조회 (로그인)
	 * @param id	로그인페이지에서 입력한 id 값
	 * @param pw	로그인페이지에서 입력한 pw 값
	 * @return true / false	  DB에서 id, pw를 비교한 후 결과
	 */
	@Override
	public boolean loginCheck(String id, String pw) {
		MemberEntity member = mr.findById(id)
								.orElseThrow(() -> new EntityNotFoundException("없는 ID"));
		if (member.getPw().equals(pw))
			return true;
		return false;
	}

	/**
	 * 
	 */
	@Override
	public void updateMember(MemberDTO memberOld, MemberDTO memberNew) {
		MemberEntity member = mr.findById(memberOld.getId())
				.orElseThrow(() -> new EntityNotFoundException("없는 ID"));
		
		member.setPw(memberNew.getPw());
		member.setName(memberNew.getName());
		member.setPhone(memberNew.getPhone());
		member.setAddress(memberNew.getAddress());
		mr.save(member);
	}
}
