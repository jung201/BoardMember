package jung.gyu.user.service;

import jung.gyu.user.dao.LoginDAO;
import jung.gyu.user.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    @Autowired
    LoginDAO loginDAO;

    @Transactional
    public LoginVO authenticate(String uId, String uPwd) {
        return loginDAO.findUser(uId, uPwd);
    }
}

