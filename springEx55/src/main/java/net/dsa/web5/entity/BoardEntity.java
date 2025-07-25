package net.dsa.web5.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="web5_board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_num")
    private Integer boardNum;

    // FetchType.LAZY: 지연로딩
    // 관련 엔티티가 실제로 접근될 때 데이터 로딩
    // 다대일 관계. 게시글 여러개가 회원정보 하나를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;

    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @Column(name = "contents", nullable = false, columnDefinition = "text")
    private String contents;

    @Column(name = "view_count", columnDefinition = "integer default 0")
    private Integer viewCount;

    @Column(name = "like_count", columnDefinition = "integer default 0")
    private Integer likeCount;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "file_name")
    private String fileName;

    @CreatedDate
    @Column(name="create_date", columnDefinition = "timestamp default" + "current_timestamp")
    private LocalDateTime createDate;

    @Column(name = "update_date", columnDefinition = "timestamp default" + "current_timestamp")
    private LocalDateTime updateDate;

    // 일대다 관계. 댓글 여러개가 게시글 하나를 참조
    /*
        mappedBy : ReplyEntity의 board 멤버 변수와 매핑
        cascade : 게시글이 삭제되거나 변경될 때 댓글도 같이 삭제 or 변경
        orphanRemoval : 게시글에서 댓글이 삭제되면, DB도 해당 댓글 삭제
    */
    @OneToMany(mappedBy = "board",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private List<ReplyEntity> replyList;

    // 멤버 변수 초기화 하는 메서드
    @PrePersist
    public void prePersist() {
        if (viewCount == null) this.viewCount = 0;
        if (likeCount == null) this.likeCount = 0;
    }
}
