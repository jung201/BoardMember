package jung.gyu.user.dao;

import jung.gyu.user.vo.LoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginDAO {
    LoginVO findUser ( @Param("uId") String uId, @Param("uPwd") String uPwd );
}
