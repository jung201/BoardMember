package jung.gyu.user.dao;


import jung.gyu.user.vo.SignupVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignupDAO {

    // 1. 사용자 정보입력
    void insertUser(SignupVO signupVO);
}
