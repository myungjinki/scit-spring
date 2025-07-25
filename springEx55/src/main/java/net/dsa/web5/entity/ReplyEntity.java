package net.dsa.web5.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 리플 정보 엔티티
 */
@Builder
@Getter
@Setter
// 순환 참조 문제를 피하기 위해서.
// 한쪽만 toString()에서 제외해도 무한순환참조를 막을 수 있음.
@ToString(exclude = "board")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "web5_reply")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_num")
    private Integer replyNum;

    // 다대일 관계. 리플 여러개가 회원정보 하나를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;

    @Column(name = "contents", nullable = false, length = 2000)
    private String contents;

    @CreatedDate
    @Column(name = "createdate", columnDefinition = "timestamp default " + "current_timestamp")
    private LocalDateTime createDate;

    // 다대일 관계. 여러 댓글이 하나의 게시글 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_num")
    private BoardEntity board;
}
