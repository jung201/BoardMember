package jung.gyu.board.controller;

import jung.gyu.board.service.BoardService;
import jung.gyu.board.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        // 카테고리 변환
        if(category == null || category.isEmpty()) {
            boardList = boardService.getAllBoard(page);
        } else {
            boardList = boardService.getBoardByCategory(page, category);
        }

        // 페이지네이션
        int totalPages = boardService.getTotalPages(category);

        Map<String, Object> response = new HashMap<>();
        response.put("boardList", boardList);
        response.put("totalPages", totalPages);

        return response;
    }
}
