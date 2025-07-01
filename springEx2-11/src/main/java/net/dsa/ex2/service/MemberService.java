package net.dsa.ex2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.dsa.ex2.dto.Member;

@Service("MemberServcie")
public class MemberService {
	
	// 회원목록
	private List<Member> memberList = new ArrayList<>();
	
	// 회원가입
	public boolean save(Member member) {
		for (Member m : memberList) {
			if (m.getId().equals(member.getId())) {
				return false;
			}
		}
		
		memberList.add(member);
		return true;
	}

	// 회원 확인
	public boolean loginCheck(String id, String pw) {
		if (memberList.isEmpty()) {
			return false;
		}
		
		for (Member m : memberList) {
			if (m.getId().equals(id) && m.getPw().equals(pw)) {
				return true;
			}
		}
		
		return false;
	}

	// 회원목록 조회
	public List<Member> selectList() {
		return this.memberList;
	}
	
}
