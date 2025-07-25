package net.dsa.web4.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guestbook")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*
	@EntityListeners(AuditingEntityListener.class)
	Spirng Data JPA의 Auditing 기능을 사용하기 위해 
	엔티티 클래스에 추가하는 Annotation
	 - Entity의 생성 및 수정 시점에 자동으로 특정 필드(생성일, 수정일 ..)를 업데이트
	 - JPA의 Auditing 기능 : Entity의 생성 및 수정 시점에 자동으로 특정필드를
	 					    기록할 수 있도록 도와주는 기능.
	 Ex. Entity 클래스의 멤버변수에 붙는 Annotation들..
	 	@CreatedDate		최초 저장시 시간 자동 저장
	 	@LastModifiedDate	수정될 때마다 시간 자동 갱신
	 	@CreatedBy			최초 저장시 작성자 저장
	 	@LastModifiedBy		수정 시 작성자 저장
 */
@EntityListeners(AuditingEntityListener.class)
public class GuestBookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="num")
	private Integer num;		// 글 번호
	
	@Column(name="name", nullable = false, length = 100)
	String name;				// 이름
	
	@Column(name="password", nullable = false, length = 100)
	String password;			// 비밀번호
	
	@Column(name="message", nullable = false, columnDefinition = "text")
	String message;				// 글 내용
	
	@CreatedDate		// LocalDateTime, LocalDate, Date 타입과 함께 사용
	@Column(name="inputdate")
	LocalDateTime inputdate;	// 작성시간
	
	
}
