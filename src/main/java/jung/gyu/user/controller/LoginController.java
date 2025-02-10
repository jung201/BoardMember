package jung.gyu.user.controller;

import jakarta.servlet.http.HttpSession;
import jung.gyu.user.service.LoginService;
import jung.gyu.user.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam("uId") String uId,
            @RequestParam("uPwd") String uPwd,
            HttpSession session) {

        ModelAndView mav = new ModelAndView(); // ModelAndView 객체 생성

        LoginVO user = loginService.authenticate(uId, uPwd); // DB에서 사용자 조회

        if (user != null) {
            System.out.println("로그인 성공 ID : " + uId );
            session.setAttribute("loggedInUser", user);  // 세션에 사용자 정보 저장
            mav.setViewName("redirect:/main"); // 메인 컨트롤러로 리다이렉트

        } else {
            System.out.println("로그인 실패 ID : " + uId );
            mav.setViewName("user/login"); // 다시 로그인 페이지로 이동
            mav.addObject("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        return mav; // ModelAndView 반환 (데이터 + 화면 정보)

    }
}
