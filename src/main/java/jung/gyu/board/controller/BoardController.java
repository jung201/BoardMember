package jung.gyu.board.controller;

import jung.gyu.board.service.BoardService;
import jung.gyu.board.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;
    
    // 1. 전체 게시물 조회
    @GetMapping
    public String showBoardPage(Model model) {
        System.out.println("\n---board---\n");
        model.addAttribute("boardList", new ArrayList<BoardVO>());
        return "board/board";
    }
}
