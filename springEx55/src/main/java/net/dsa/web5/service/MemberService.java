package net.dsa.web5.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.MemberDTO;
import net.dsa.web5.entity.MemberEntity;
import net.dsa.web5.repository.MemberRepository;
import net.dsa.web5.security.WebSecurityConfig;


/**
 * 회원 관련 로직 처리 서비스
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final WebSecurityConfig webSecurityConfig;

	// 회원정보 DB 작업 
	private final MemberRepository mr;
	
	// 암호화
	private final BCryptPasswordEncoder passwordEncoder;

	
	public boolean idCheck(String searchId) {
		// TODO Auto-generated method stub
		return !mr.existsById(searchId);
	}

	public void join(MemberDTO member) {
		
		MemberEntity entity = MemberEntity.builder()
							 .memberId(member.getMemberId())
							 .memberPassword(passwordEncoder.encode(member.getMemberPassword()))
							 .memberName(member.getMemberName())
							 .email(member.getEmail())
							 .phone(member.getPhone())
							 .address(member.getAddress())
							 .enabled(true)
							 .rolename("ROLE_USER")
							 .build();
			mr.save(entity);
	}

	/*
	 * 회원정보 조회
	 * @param username 회원아이디
	 * @return 회원정보
	 */
	public MemberDTO getMember(String username) {
		
		MemberEntity entity = mr.findById(username).orElseThrow(
				() -> new EntityNotFoundException(username + ": 아이디가 없습니다."));
		
		MemberDTO dto = MemberDTO.builder()
						.memberId(entity.getMemberId())
						.memberName(entity.getMemberName())
						.email(entity.getEmail())
						.phone(entity.getPhone())
						.address(entity.getAddress())
						.enabled(entity.getEnabled())
						.rolename(entity.getRolename())
						.build();
		return dto;
	}
	/* 
	 * 개인정보 수정 처리
	 * @param memberDTO 수정할 정보
	 */
	public void update(MemberDTO memberDTO) {
		
		MemberEntity entity = mr.findById(memberDTO.getMemberId()).orElseThrow(
							()-> new EntityNotFoundException(memberDTO.getMemberId() +": 아이디가 없습니다"));
		
		// @Transactional 안에서 필드를 변경하면
		// JPA가 변경 감지를 통해 UPDATE를 실행
		// memberDTO의 비밀번호가 비어있지 않으면 비밀번호 수정
		if (!memberDTO.getMemberPassword().isEmpty()) {
			entity.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));
		}
		entity.setMemberName(memberDTO.getMemberName());
		entity.setEmail(memberDTO.getEmail());
		entity.setPhone(memberDTO.getPhone());
		entity.setAddress(memberDTO.getAddress());
//		mr.save(entity);
		
	}

	public List<MemberDTO> selectAll() {
		
		Sort sort = Sort.by(
			Sort.Order.asc("rolename"),
			Sort.Order.desc("memberName")
			);
		List<MemberEntity> entityList = mr.findAll(sort);
		log.debug("entityList: {}", entityList);
		List<MemberDTO> dtoList = new ArrayList<>();
		
		for (MemberEntity entity : entityList) {
			MemberDTO dto = MemberDTO.builder().build();
			dto.setMemberId(entity.getMemberId());
			dto.setMemberName(entity.getMemberName());
			dto.setEmail(entity.getEmail());
			dto.setPhone(entity.getPhone());
			dto.setAddress(entity.getAddress());
			dto.setEnabled(entity.getEnabled());
			dto.setRolename(entity.getRolename());
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	/*
	 * 권한변경처리
	 * @param memberid
	 */
	public void upgrade(String memberId) {
		
		MemberEntity entity = mr.findById(memberId).orElseThrow(
							() -> new EntityNotFoundException("회원이 없다"));	
		
		String updateRolename = entity.getRolename()
								.equals("ROLE_USER") ? "ROLE_ADMIN" : "ROLE_USER";
		entity.setRolename(updateRolename);
	}
	/*
	 * 계정 활성화/ 비활성화 처리
	 * @param memberId
	 * @param enabled
	 */
	public void updateEnabled(String memberId, boolean enabled) {
		
		MemberEntity entity = mr.findById(memberId).orElseThrow(
						() -> new EntityNotFoundException("회원이 없다"));
		
		entity.setEnabled(enabled);
	}
	
}
