package jung.gyu.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jung.gyu.board.service.BoardService;
import jung.gyu.board.vo.BoardVO;
import jung.gyu.user.util.UserSessionUtil;
import jung.gyu.user.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    @Autowired
    BoardService boardService;

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

    // 검색 기능
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

    // 게시글 등록
    @PostMapping("/write")
    public Map<String, Object> writePost(@RequestBody BoardVO boardVO, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        // 공통 유틸을 사용해 로그인 유저 가져오기
        HttpSession session = request.getSession();
        LoginVO loggedInUser = UserSessionUtil.getLoggedInUser(session);

        if (loggedInUser != null) {
            System.out.println("\n✅ 로그인된 사용자: " + loggedInUser);
            System.out.println("\n✅ 게시글이 성공적으로 등록되었습니다\n" + boardVO +"\n");

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

}
