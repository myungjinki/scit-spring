package net.dsa.web5.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.dsa.web5.entity.BoardEntity;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import net.dsa.web5.dto.BoardDTO;
import net.dsa.web5.dto.ReplyDTO;
import net.dsa.web5.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequestMapping("board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService bs;

    @Value("${board.uploadPath}")
    String uploadPath;          // 첨부파일 저장 경로

    /**
     * 글쓰기 폼으로 이동
     * @return writeForm.html
     */
    @GetMapping("write")
    public String write() {
        return "board/writeForm";
    }

    /**
     * 글 저장
     * @param boardDTO 작성한 글 정보(제목, 내용)
     * @param upload 업로드 파일
     * @param user 로그인한 사용자 정보
     * @return 게시판 글 목록(일단 메인 페이지)
     * MultipartFile
     *  - 스프링에서 파일 업로드를 처리할 때 사용하는 인터페이스
     *  - HTTTP 요청으로 전송된 업로드 파일을 스프링이 객체로 매핑
     */
    @PostMapping("write")
    public String write(BoardDTO boardDTO,
                        @RequestParam(name = "upload", required = false)
                        MultipartFile upload,
                        @AuthenticationPrincipal UserDetails user
                  		) {

        boardDTO.setMemberId(user.getUsername());
        log.debug("저장할 글 정보: {}", boardDTO);
        log.debug("업로드한 파일의 원봉 이름: {}", upload.getOriginalFilename());
        log.debug("파일 크기: {}", upload.getSize());
        log.debug("파일이 비어있는지 여부: {}", upload.isEmpty());

        try {
            bs.write(boardDTO, uploadPath, upload);
            return "redirect:list";

        } catch(Exception e) {
            log.debug("[예외 발생] 파일 업로드 실패..");
            return "redirect:board/writeForm";
        }
    }

    /**
     * 게시판 글 목록 페이지 이동
     * @param Model
     * @return list.html
     */
    @GetMapping("list")
    public String list(Model model) {

        try {
            List<BoardDTO> boardList = bs.getList();
            model.addAttribute("boardList", boardList);
            log.debug("전체 조회 성공!");
        } catch (Exception e) {
            log.debug("[예외 발생] 전체 조회 실패...");
        }

        return "board/list";
    }

    /**
     * 게시글 상세보기
     * @param boardNum
     * @param Model
     * @return readForm.html
     */
    @GetMapping("read")
    public String read(Model model,
                       @RequestParam(name="boardNum",defaultValue = "0") int boardNum) {
        log.debug("조회할 글 번호: {}", boardNum);

        try {
            BoardDTO boardDTO = bs.getBoard(boardNum, true);
            model.addAttribute("board", boardDTO);
            log.debug("조회한 글 정보: {}", boardDTO);
            return "board/read";
        } catch (Exception e) {
            log.debug("[예외 발생] 글 정보 조회 실패..");
            return "redirect:list";
        }
    }

    /**
     * 첨부파일 다운로드
     * @param boardNum 게시글 번호
     * @param response 응답 객체
     */
    @GetMapping("download")
    public void download(
            @RequestParam("boardNum") int boardNum,
            HttpServletResponse response) {
        try {
            bs.download(boardNum, response, uploadPath);
            log.debug("다운로드 성공");
        } catch (Exception e) {
            log.debug("[예외 발생] 다운로드 실패..");
        }
    }

    /**
     * 게시글 추천
     * @param boardNum 추천할 게시글의 번호
     * @return 글목록
     */
    @GetMapping("like")
    public String like(
            @RequestParam("boardNum") int boardNum) {
        try {
            bs.like(boardNum);
            log.debug("추천 성공!");
            return "redirect:read?boardNum=" + boardNum;
        } catch (Exception e) {
            log.debug("[예외 발생] 추천 실패..");
            return "redirect:list";
        }
    }

    /**
     * 게시글 수정 폼으로 이동
     * @param boardNum 수정할 글 번호
     * @param user 로그인한 사용자 정보
     * @param Model
     * @return updateForm.html
     */
    @GetMapping("update")
    public String update(@RequestParam("boardNum") int boardNum,
                         @AuthenticationPrincipal UserDetails user,
                         Model model) {

        try {
            BoardDTO boardDTO = bs.getBoard(boardNum, false);
            if (!user.getUsername().equals(boardDTO.getMemberId())) {
                throw new RuntimeException("수정 권한이 없습니다.");
            }
            model.addAttribute("board", boardDTO);
            return "board/updateForm";
        } catch (Exception e) {
            log.debug("[예외 발생] {}", e.getMessage());
            return "redirect:list";
        }
    }

    /**
     * 게시글 수정 처리
     * @param boardDTO 수정할 글 정보
     * @param user 로그인한 사용자 정보
     * @param upload 첨부파일
     * @return 글 읽기 페이지로 이동
     */
    @PostMapping("update")
    public String update(BoardDTO boardDTO,
                         @AuthenticationPrincipal UserDetails user,
                         @RequestParam(name="upload", required = false) MultipartFile upload) {
        boardDTO.setMemberId(user.getUsername());
        try {
            bs.update(boardDTO, uploadPath, upload);
            log.debug("수정 성공");
            return "redirect:read?boardNum=" + boardDTO.getBoardNum();
        } catch (Exception e) {
            log.debug("[예외 발생] {}", e.getMessage());
            return "redirect:list";
        }
    }
    
    /**
     * 게시글 삭제
     * @param boardNum	삭제할 글번호
     * @param user		로그인한 사용자 정보
     * @return			글 목록
     */
    @GetMapping("delete")
    public String delete(
    		@RequestParam("boardNum") int boardNum,
    		@AuthenticationPrincipal UserDetails user) {
    	
    	try {
    		bs.delete(boardNum, uploadPath, user.getUsername());
    		log.debug("삭제 성공");
    	} catch (Exception e) {
    		log.debug("삭제 실패");
    	}
    	
    	return "redirect:list";
    }
    
	
	/**
	 * 리플 저장
	 * @Param replyDTO	저장할 리플 정보
	 * @param user		로그인 사용자 정보
	 * @return 			게시글 읽기 페이지로 이동
	 */
	@PostMapping("replyWrite")
	public String replyWrite(
			ReplyDTO replyDTO,
			@AuthenticationPrincipal UserDetails user) {
		
		try {
			replyDTO.setMemberId(user.getUsername());
			bs.replyWrite(replyDTO);
			log.debug("댓글 작성 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생] {}", e.getMessage());			
		}
		return "redirect:read?boardNum=" + replyDTO.getBoardNum();
	}
	
	/**
	 * 리플 삭제
	 * @Param replyDTO	삭제할 리플번호와 본문 글번호
	 * @Param user 		로그인한 사용자 정보
	 * @return 게시글 읽기 페이지
	 */
	@GetMapping("replyDelete")
	public String replyDelete(
				ReplyDTO replyDTO,
				@AuthenticationPrincipal UserDetails user) {
		try {
			bs.replyDelete(replyDTO.getReplyNum(), user.getUsername());
			log.debug("댓글 삭제 성공!");
		} catch (Exception e) {
			log.debug("[예외 발생] {}", e.getMessage());			
		}
		return "redirect:read?boardNum=" + replyDTO.getBoardNum();
	}
}