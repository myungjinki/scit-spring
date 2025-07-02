package net.dsa.web3.service;

import java.util.List;

import net.dsa.web3.dto.MemberDTO;

public interface MemberService {

	void insertData();

	MemberDTO selectData(String string);

	void updateData(MemberDTO m);

	boolean deleteData(String string);

	List<MemberDTO> selectAllData();

	void save(MemberDTO member);

	boolean loginCheck(String id, String pw);
	/**
	 * BasicController
	 */
}
