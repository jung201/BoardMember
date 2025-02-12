package jung.gyu.user.controller;

import jung.gyu.user.service.SignupService;
import jung.gyu.user.vo.SignupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class SignupRestController {

    @Autowired
    SignupService signupService;

    // 1. 이메일 중복 확인 API
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = signupService.isEmailExists(email);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    // ===================================================================================================

    // 2. 회원가입 처리
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupVO signupVO) {
        if (signupVO == null) {
            return ResponseEntity.badRequest().body("🚨 잘못된 요청입니다. 데이터가 전달되지 않았어요!");
        }

        // ✅유효성 검사 실행
        String validationMessage = validateSignup((signupVO));
        if (validationMessage != null) {
            return ResponseEntity.badRequest().body(validationMessage);
        }

        // 🚨 이메일 중복 체크
        boolean emailExists = signupService.isEmailExists(signupVO.getUEmail());
        System.out.println("\n✅이메일 중복 여부 체크: " + emailExists + "\n");

        if (emailExists) {
            System.out.println("\n🚨 이미 존재하는 이메일입니다!" + "\n");
            return ResponseEntity.badRequest().body("🚨 이미 존재하는 이메일입니다!");
        }

    // ===================================================================================================

        System.out.println("\n✅회원가입 요청 데이터: " + signupVO + "\n");

        // ✅회원가입 진행
        signupService.registerUser(signupVO);
        System.out.println("\n✅회원가입 성공 : " + signupVO +"\n");

        return ResponseEntity.ok(signupVO);
    }

    // ===================================================================================================

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
