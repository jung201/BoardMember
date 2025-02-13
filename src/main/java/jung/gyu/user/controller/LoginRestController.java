package jung.gyu.user.controller;

import jakarta.servlet.http.HttpSession;
import jung.gyu.user.service.LoginService;
import jung.gyu.user.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    // 1. 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String uId, @RequestParam String uPwd, HttpSession session) {
        LoginVO user = loginService.authenticate(uId, uPwd);

        if ( user != null ) {
            System.out.println("\n✅로그인 성공 : " + user +"\n");
            session.setAttribute("loggedInUser", user);
            return ResponseEntity.ok(user); // 성공 시 유저 정보 반환
        } else {
            return ResponseEntity.status(401).body("로그인 실패 : 아이디 또는 비밀번호가 일치하지 않습니다");
        }
    }
}
