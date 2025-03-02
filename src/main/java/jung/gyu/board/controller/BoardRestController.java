package jung.gyu.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jung.gyu.board.service.BoardService;
import jung.gyu.board.vo.BoardVO;
import jung.gyu.user.util.UserSessionUtil;
import jung.gyu.user.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    @Autowired
    BoardService boardService;

    // 1. 게시글 리스트 조회
    @GetMapping("/list")
    public Map<String, Object> getAllBoard(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String category) {

        List<BoardVO> boardList;
        int totalPages = boardService.getTotalPages(category, null, null);

        // 카테고리 변환
        if (category == null || category.isEmpty()) {
            boardList = boardService.getAllBoard(page);
        } else {
            boardList = boardService.getBoardByCategory(page, category);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("boardList", boardList);
        response.put("totalPages", totalPages);

        return response;
    }

    // 2. 검색 기능
    @GetMapping("/search")
    public Map<String, Object> searchBoard(
            @RequestParam String searchType,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page) {

        List<BoardVO> boardList = boardService.searchBoard(searchType, keyword, page);
        int totalPages = boardService.getTotalPages(null, searchType, keyword);

        Map<String, Object> response = new HashMap<>();
        response.put("boardList", boardList);
        response.put("totalPages", totalPages);

        System.out.println("🔍 검색 요청 - searchType: " + searchType + ", keyword: " + keyword);
        return response;
    }

    // 3. 게시글 등록
    @PostMapping("/write")
    public Map<String, Object> writePost(@RequestBody BoardVO boardVO, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        // 공통 유틸을 사용해 로그인 유저 가져오기
        HttpSession session = request.getSession();
        LoginVO loggedInUser = UserSessionUtil.getLoggedInUser(session);

        if (loggedInUser != null) {
            System.out.println("\n✅ 로그인된 사용자: " + loggedInUser);
            System.out.println("\n✅ 게시글이 성공적으로 등록되었습니다\n" + boardVO + "\n");

        } else {
            response.put("success", false);
            System.out.println("\n🚨 사용자 정보를 찾을 수 없습니다!\n");
            return response;
        }

        // 닉네임 설정 후 게시글 저장
        boardVO.setBCreatedId(loggedInUser.getUNickname());
        boolean success = boardService.writePost(boardVO);

        response.put("success", success);
        return response;
    }

    // 4. 보기 팝업
    @GetMapping("/detail")
    public ResponseEntity<Map<String, Object>> getBoardDetail(@RequestParam int bNo, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginVO loggedInUser = UserSessionUtil.getLoggedInUser(session);
        BoardVO board = boardService.getBoardById(bNo);

        if (board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // 작성자 여부 확인
        boolean isAuthor = loggedInUser != null && loggedInUser.getUNickname().equals(board.getBCreatedId());

        Map<String, Object> response = new HashMap<>();
        response.put("board", board);
        response.put("isAuthor", isAuthor);

        return ResponseEntity.ok(response);
    }

    // 5. 게시글 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deletePost(@RequestParam int bNo, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginVO loggedInUser = UserSessionUtil.getLoggedInUser(session);

        Map<String, Object> response = new HashMap<>();

        if (loggedInUser == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 게시글 정보 가져오기
        BoardVO board = boardService.getBoardById(bNo);

        if (board == null) {
            response.put("success", false);
            response.put("message", "게시글이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // 작성자 확인
        if (!loggedInUser.getUNickname().equals(board.getBCreatedId())) {
            response.put("success", false);
            response.put("message", "삭제 권한이 없습니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // 게시글 삭제 실행
        boolean success = boardService.deletePost(bNo);
        response.put("success", success);
        System.out.println("\n✅게시글이 삭제되었습니다 ! " + board + "\n");

        return ResponseEntity.ok(response);
    }

    // 6. 게시글 수정
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updatePost(@RequestBody BoardVO boardVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginVO loggedInUser = UserSessionUtil.getLoggedInUser(session);
        Map<String, Object> response = new HashMap<>();

        if (loggedInUser == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        BoardVO existingBoard = boardService.getBoardById(boardVO.getBNo());
        if (existingBoard == null) {
            response.put("success", false);
            response.put("message", "게시글이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (!loggedInUser.getUNickname().equals(existingBoard.getBCreatedId())) {
            response.put("success", false);
            response.put("message", "수정 권한이 없습니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // 게시글 수정 실행
        boolean success = boardService.updatePost(boardVO);
        response.put("success", success);
        System.out.println("\n✅게시글이 수정되었습니다 ! " + boardVO + "\n");

        return ResponseEntity.ok(response);
    }

    // 7. 게시물 조회수 증가
    @PutMapping("/increaseView")
    public ResponseEntity<?> increaseViewCount(@RequestParam int bNo) {
        boardService.increaseView(bNo);
        return ResponseEntity.ok().body(Map.of("message", "조회수 증가 완료!"));
    }

}
