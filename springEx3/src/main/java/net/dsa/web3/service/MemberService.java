package net.dsa.web3.service;

import net.dsa.web3.dto.MemberDTO;

public interface MemberService {

	void insertData();

	MemberDTO selectData(String string);

	/**
	 * BasicController
	 */
}
