package jung.gyu.user.service;

import jung.gyu.user.dao.SignupDAO;
import jung.gyu.user.vo.SignupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    @Autowired
    SignupDAO signupDAO;

    // 1. 회원가입 메서드
    public void registerUser(SignupVO signupVO) {
        signupDAO.insertUser(signupVO);
    }

    // 2. 이메일 중복확인 메서드
    public boolean isEmailExists(String email) {
        int count = signupDAO.checkEmailExists(email);
        return count > 0;
    }
}
