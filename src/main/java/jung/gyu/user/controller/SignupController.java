package jung.gyu.user.controller;

import jung.gyu.user.service.SignupService;
import jung.gyu.user.vo.SignupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SignupController {

    @Autowired
    SignupService signupService;

    // 1. 회원가입 페이지 이동
    @GetMapping("/signup")
    public String showSignupPage() {
        System.out.println("---signup---");
        return "user/signup";
    }

    // 2. 회원가입 처리 (POST 처리)
    @PostMapping("/signup")
    public String registerUser(SignupVO signupVO) {
        signupService.registerUser(signupVO);
        System.out.println("회원가입 성공 : " + signupVO);
        return "user/login"; // 회원가입 후 login 페이지 이동
    }
}
