package jung.gyu.user.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;

@Data
public class SignupVO {
    @JsonProperty("uId")
    private String uId;         // 사용자 ID

    @JsonProperty("uPwd")
    private String uPwd;        // 사용자 비밀번호

    @JsonProperty("uEmail")
    private String uEmail;      // 사용자 이메일

    @JsonProperty("uNickname")
    private String uNickname;   // 사용자 닉네임
    private String uPhotoName;  // 사용자 프로필 사진 이름
    private String uPhotoPath;  // 사용자 프로필 사진 경로
    private Date createdDate;   // 사용자 생성일
    private Date updateDate;    // 사용자 수정일
}
