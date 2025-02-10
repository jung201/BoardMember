package jung.gyu.user.service;

import jung.gyu.user.dao.LoginDAO;
import jung.gyu.user.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginDAO loginDAO;

    public LoginVO authenticate(String uId, String uPwd) {
        LoginVO user = loginDAO.findUser(uId, uPwd);
        System.out.println("DB 조회 - ID: " + uId + ", PWD: " + uPwd);

        if (user == null) {
            System.out.println("DB 조회 결과: 사용자 정보 없음 ❌");
            return null;

        } else {
            System.out.println("DB 조회 결과: 사용자 정보 있음 ✅");
            System.out.println("사용자 ID: " + user.getUId());
            System.out.println("사용자 닉네임: " + user.getUNickname());
        }
        return user;
    }
}

