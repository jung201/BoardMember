package jung.gyu.user.controller;

import jung.gyu.user.service.SignupService;
import jung.gyu.user.vo.SignupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class SignupRestController {

    @Autowired
    SignupService signupService;

    // 1. 회원가입 처리
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupVO signupVO) {
        if (signupVO == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다. 데이터가 전달되지 않았어요!");
        }

        // ✅유효성 검사 실행
        String validationMessage = validateSignup((signupVO));
        if (validationMessage != null) {
            return ResponseEntity.badRequest().body(validationMessage);
        }

        System.out.println("✅회원가입 요청 데이터: " + signupVO);

        // ✅회원가입 진행
        signupService.registerUser(signupVO);

        System.out.println();
        System.out.println("✅회원가입 성공 : " + signupVO);
        System.out.println();

        return ResponseEntity.ok(signupVO);
    }

    // ✅유효성 검사 메서드
    private String validateSignup(SignupVO signupVO) {
        if (!signupVO.getUId().matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{1,12}$")) {
            return "아이디는 문자+숫자로 12자 이내여야 해요!";
        }
        if (!signupVO.getUPwd().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{1,16}$")) {
            return "비밀번호는 16자 이내, 대문자 & 특수문자를 포함해야 해요!";
        }
        if (!signupVO.getUEmail().matches("^(?=.*[a-zA-Z])(?=.*@)(?=.*\\.com)[a-zA-Z0-9@.]+$")) {
            return "올바른 이메일 형식을 입력해주세요! (@와 .com 포함 필수)";
        }
        if (!signupVO.getUNickname().matches("^[가-힣a-zA-Z]+$")) {
            return "닉네임은 숫자와 특수문자를 포함할 수 없어요!";
        }
        return null; // 유효성 검사 통과
    }
}
