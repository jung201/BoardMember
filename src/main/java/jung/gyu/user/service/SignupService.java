package jung.gyu.user.service;

import jung.gyu.user.dao.SignupDAO;
import jung.gyu.user.vo.SignupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    @Autowired
    SignupDAO signupDAO;

    public void registerUser(SignupVO signupVO) {
        signupDAO.insertUser(signupVO);
    }
}
