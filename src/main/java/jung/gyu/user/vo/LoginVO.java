package jung.gyu.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class LoginVO {
    private String uId;         // 사용자 아이디
    private String uPwd;        // 사용자 비밀번호
    private String uEmail;      // 이메일

    @JsonProperty("uNickname")
    private String uNickname;   // 닉네임

    private String uPhotoName;  // 프로필 사진 파일명
    private String uPhotoPath;  // 프로필 사진 경로
    private Date createdDate;   // 계정 생성일
    private Date updatedDate;   // 계정 정보 수정일
}
