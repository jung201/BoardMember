package jung.gyu.board.controller;

import jakarta.servlet.http.HttpSession;
import jung.gyu.board.service.BoardService;
import jung.gyu.board.vo.BoardVO;
import jung.gyu.user.util.UserSessionUtil;
import jung.gyu.user.vo.LoginVO;
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

    // 2. 게시물 작성
    @GetMapping("/write")
    public String showWritePage(HttpSession session, Model model) {
        LoginVO loggedInUser = UserSessionUtil.getLoggedInUser(session);

        if (loggedInUser != null) {
            model.addAttribute("uNickname", loggedInUser.getUNickname());
        } else {
            model.addAttribute("uNickname", "게스트");
        }

        return "board/boardWrite";  // boardWrite.jsp로 이동
    }
}
