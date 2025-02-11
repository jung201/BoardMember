package jung.gyu.user.controller;

import jung.gyu.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    LoginService loginService;

    // 로그인 페이지 GET 요청 처리
    @GetMapping("/login")
    public String showLoginPage() {
        System.out.println("---login---");
        return "user/login"; // login.jsp 파일 반환
    }
}

