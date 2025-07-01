package net.dsa.web3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 해당 클래스를 엔티티로 정의하여 JPA가 해당 클래스를 DB 테이블과 매핑
@Entity
// 엔티티가 매핑될 테이블 지정. 생략하면 클래스 이름을 테이블 이름으로 사용
@Table(name="member3")
public class MemberEntity {
	
	/*
	 * JPA - Java Persistence API
	 *   Java의 객체(Entity)를 관계형 데이터베이스에 영속(persist)
	 *   시키기 위한 표준 API
	 *   Java 언어로 데이터를 DB에 영구화 하기 위한 API
	 */
	
	// Entity 클래스에는 반드시 @Id 를 사용하여 식별자 필드를 지정해야 함
	// 해당 멤버변수를 Entity의 식별자(primary key)로 지정
	@Id
	@Column(name="id", nullable = false, length = 30)
	private String id;
	@Column(name="pw", length = 50)
	private String pw;
	@Column(name="name")
	private String name;
	@Column(name="phone")
	private String phone;
	@Column(name="address")
	private String address;
}
