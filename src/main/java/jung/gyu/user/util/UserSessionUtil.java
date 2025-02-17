package jung.gyu.user.util;

import jakarta.servlet.http.HttpSession;
import jung.gyu.user.vo.LoginVO;

public class UserSessionUtil {
    public static LoginVO getLoggedInUser(HttpSession session) {
        return (LoginVO) session.getAttribute("loggedInUser");
    }
}
