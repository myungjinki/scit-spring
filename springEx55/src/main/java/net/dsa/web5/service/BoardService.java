package net.dsa.web5.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.BoardDTO;
import net.dsa.web5.dto.ReplyDTO;
import net.dsa.web5.entity.BoardEntity;
import net.dsa.web5.entity.MemberEntity;
import net.dsa.web5.entity.ReplyEntity;
import net.dsa.web5.repository.BoardRepository;
import net.dsa.web5.repository.MemberRepository;
import net.dsa.web5.repository.ReplyRepository;
import net.dsa.web5.util.FileManager;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    private final MemberRepository mr;
    private final BoardRepository br;
    private final ReplyRepository rr;
    private final FileManager fileManager;

    /**
     * 글 저장
     * @param boardDTO
     * @param uploadPath
     * @param upload
     */
    public void write(BoardDTO boardDTO, String uploadPath, MultipartFile upload) throws IOException {
        // 글 작성자 조회
        MemberEntity memberEntity =
                mr.findById(boardDTO.getMemberId()).orElseThrow(
                        () -> new EntityNotFoundException("회원이 없습니다."));
        
        // 게시글 저장
        BoardEntity entity = BoardEntity.builder().build();
        entity.setMember(memberEntity);
        entity.setTitle(boardDTO.getTitle());
        entity.setContents(boardDTO.getContents());

        // 첨부파일이 있는 경우
        if (upload != null && !upload.isEmpty()){
            String fileName;
            fileName = fileManager.saveFile(uploadPath, upload);
            entity.setFileName(fileName);
            entity.setOriginalName(upload.getOriginalFilename());
        }
        log.debug("저장되는 엔티티: {}", entity);
        br.save(entity);
    }

    /**
     * 게시글 전체 조회
     * @return 글 목록
     */
    public List<BoardDTO> getList() {
        Sort sort = Sort.by(Sort.Direction.DESC, "boardNum");
        List<BoardEntity> entityList = br.findAll(sort);
        List<BoardDTO> dtoList = new ArrayList<>();
        for (BoardEntity entity : entityList) {
            BoardDTO dto = BoardDTO.convertToBoardDTO(entity);
            dtoList.add(dto);
        }

        return dtoList;
    }

    /**
     * 게시글 상세 조회
     * @param boardNum 글 번호
     * @param read 읽기 요청일때만 조회수 증가를 위한 flag
     * @return 글 정보
     */
    public BoardDTO getBoard(int boardNum, boolean read) {
        BoardEntity entity = br.findById(boardNum)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 번호의 글이 없습니다.")
                );

        if (read) {
            entity.setViewCount(entity.getViewCount() + 1);
        }

        BoardDTO dto = BoardDTO.convertToBoardDTO(entity);
        
        // 댓글 데이터 추가
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        for (ReplyEntity replyEntity : entity.getReplyList()) {
        	ReplyDTO replyDTO = ReplyDTO.convertToReplyDTO(replyEntity);
        	replyDTOList.add(replyDTO);
        }
        dto.setReplyList(replyDTOList);

        return dto;
    }

    public void plusViewCount(int boardNum) {
        BoardEntity boardEntity = br.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("찾는 게시글이 없습니다."));
        boardEntity.setViewCount(boardEntity.getViewCount() + 1);

        br.save(boardEntity);
    }

    /**
     * 파일 다운로드
     * @param boardNum 글 번호
     * @param response 응답 객체(HTTP 응답으로 파일 전송)
     * @param uploadPath 파일 저장 경로
     */
    public void download(int boardNum,
                         HttpServletResponse response,
                         String uploadPath) {
        BoardEntity boardEntity = br.findById(boardNum)
                .orElseThrow(
                        () -> new EntityNotFoundException("게시글이 없습니다.")
                );
        try {
            /*
                Content-Disposition: 브라우제에게 응답에 포함된 컨텐츠를
                    어떻게 처리해야 할 지 지시하는 HTTP 헤더.
                attachment: 브라우저가 해당 파일을 다운로드하도록 지시
                filename: 다운로드 창에 표시될 파일 이름 지정
                URLEncoder.encode: 파일 이름에 한글, 공백, 특수문자가 포함되어 있을 경우 문제가 발생될 수 있어 UTF-8로 인코딩
            */
            response.setHeader("Content-Disposition",
                    "attachment; filename="
                            + URLEncoder.encode(boardEntity.getOriginalName(),
                            "UTF-8"
            ));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 저장된 파일 경로
        String fullPath = uploadPath + "/" + boardEntity.getFileName();

        // 서버 파일을 읽기 위한 FileInputStream
        FileInputStream filein = null;
        // 클라이언트에게 파일을 전송하기 위한 출력 스트림
        ServletOutputStream fileout = null;
        try {
            filein = new FileInputStream(fullPath);
            fileout = response.getOutputStream();

            // 입력 스트림의 데이터를 출력 스트림으로 복사
            FileCopyUtils.copy(filein, fileout);

            filein.close();
            fileout.close();
        } catch (IOException e) {
            log.debug("[예외 발생] 지정된 파일을 찾을 수 없습니다.");
        }
    }

    /**
     * 추천 수 증가
     * @param boardNum
     */
    public void like(int boardNum) {

        BoardEntity entity = br.findById(boardNum)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 번호의 글이 없습니다.")
                );
        entity.setLikeCount(entity.getLikeCount() + 1);
        entity.setViewCount(entity.getViewCount() - 1);
    }

    /**
     * 게시글 수정
     * @param boardDTO      수정할 글 정보
     * @param uploadPath    파일을 저장할 경로
     * @param upload        첨부파일
     */
    public void update(BoardDTO boardDTO,
                       String uploadPath, MultipartFile upload) throws Exception {
        BoardEntity boardEntity = br.findById(boardDTO.getBoardNum()).orElseThrow(
                () -> new EntityNotFoundException("게시글이 없습니다.")
        );
        // DB에 저장된 게시글 작성자와 수정하려고 하는 로그인 사용자가 같은지 비교
        if (!boardEntity.getMember().getMemberId().equals(boardDTO.getMemberId())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        // 전달된 정보 수정
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContents(boardDTO.getContents());
        boardEntity.setUpdateDate(LocalDateTime.now());

        // 업로드된 파일이 있으면 기존 파일 삭제하고 새로 저장
        if (upload != null && !upload.isEmpty()){
            // 기존 파일이 존재 한다면 삭제
            if (boardEntity.getFileName() != null) {
                fileManager.deleteFile(
                        uploadPath,                 // 파일이 저장딘 경로
                        boardEntity.getFileName()   // 파일 이름
                );
            }
            String fileName = fileManager.saveFile(uploadPath, upload);
            boardEntity.setOriginalName(upload.getOriginalFilename());
            boardEntity.setFileName(fileName);
        }
        // 업로드 하고자 하는 파일이 없는 경우
        else {
            // 기존 DB에 첨부된 파일이 있는 경우
            if (boardEntity.getFileName() != null) {
                fileManager.deleteFile(
                        uploadPath, boardEntity.getFileName()
                );
            }
            boardEntity.setOriginalName(null);
            boardEntity.setFileName(null);
        }
    }

    /**
     * 게시글 삭제
     * @param boardNum		삭제할 글 번호
     * @param uploadPath	파일 경로
     * @param username		로그인한 유저 아이디
     */
	public void delete(int boardNum, String uploadPath, String username) throws Exception {
		BoardEntity boardEntity = br.findById(boardNum)
				.orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
		
		if (!boardEntity.getMember().getMemberId().equals(username)) {
			throw new RuntimeException("삭제 권한이 없습니다.");
		}
		
		if (boardEntity.getFileName() != null) {
			fileManager.deleteFile(uploadPath, boardEntity.getFileName());
		}
		
		br.delete(boardEntity);
	}

	/**
	 * 리플 저장
	 * @param replyDTO 작성한 리플 정보
	 */
	public void replyWrite(ReplyDTO replyDTO) {
		
		MemberEntity memberEntity = mr.findById(replyDTO.getMemberId()).orElseThrow(() ->
				new EntityNotFoundException("사용자 아이디가 없습니다.")
		);
		BoardEntity boardEntity = br.findById(replyDTO.getBoardNum()).orElseThrow(() ->
				new EntityNotFoundException("게시글이 없습니다.")
		);
		ReplyEntity entity = ReplyEntity.builder()
				.member(memberEntity)
				.board(boardEntity)
				.contents(replyDTO.getContents())
				.build();
		rr.save(entity);
	}

	public void replyDelete(Integer replyNum, String username) {
		ReplyEntity replyEntity = rr.findById(replyNum).orElseThrow(() -> 
			new EntityNotFoundException("리플이 없습니다.")
		);
		
		if (!replyEntity.getMember().getMemberId().equals(username)) {
			throw new RuntimeException("삭제 권한이 없습니다.");
		}
		rr.delete(replyEntity);
	}
}