package jung.gyu.user.controller;

import jung.gyu.user.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SignupController {

    @Autowired
    SignupService signupService;

    @GetMapping("/signup")
    public String showSignuppage() {
        System.out.println("---signup---");
        return "user/signup";
    }
}
