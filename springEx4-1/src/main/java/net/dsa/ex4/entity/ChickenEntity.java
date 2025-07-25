package net.dsa.ex4.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chicken_order")
@EntityListeners(AuditingEntityListener.class)
public class ChickenEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;					// PK

    private String chickenType;			// 치킨 종류
    private int chickenPrice;			// 치킨 가격
    private int quantity;				// 수량
    private String extraOptions;		// 추가 옵션
    private int extraTotalPrice;		// 추가 옵션 가격
    private String deliveryType;		// 배달 종류
    private int deliveryPrice;			// 배달 비용
    private int totalPrice;				// 총 결제 금액

    @CreatedDate
    private LocalDateTime orderDate;	// 주문 시간
}
