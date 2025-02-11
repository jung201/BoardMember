package jung.gyu.main.controller;

import jakarta.servlet.http.HttpSession;
import jung.gyu.user.vo.LoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping
    public String showMainPage(HttpSession session, Model model) {
        LoginVO loggedInUser = (LoginVO) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            model.addAttribute("uNickname", loggedInUser.getUNickname());
        } else {
            model.addAttribute("uNickname", "게스트");
        }

        return "main/main";  // main.jsp 페이지 반환
    }
}

