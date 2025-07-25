package net.dsa.web5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dsa.web5.entity.ReplyEntity;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    private Integer replyNum;           // 리플 일련번호
    private Integer boardNum;           // 게시글 일련번호
    private String memberId;            // 작성자 아이디
    private String memberName;          // 작성자 이름
    private String contents;            // 리플 내용
    private LocalDateTime createDate;   // 작성 시간

    public static ReplyDTO convertToReplyDTO(ReplyEntity entity) {
        return ReplyDTO.builder()
                .replyNum(entity.getReplyNum())
                .boardNum(entity.getBoard().getBoardNum())
                .memberId(entity.getMember().getMemberId())
                .memberName(entity.getMember().getMemberName())
                .contents(entity.getContents())
                .createDate(entity.getCreateDate())
                .build();
    }

}
