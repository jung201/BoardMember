package jung.gyu.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LoginVO {
    private String uId;
    private String uPwd;
    private String uEmail;
    private String uNickname;
    private String uPhotoName;
    private String uPhotoPath;
    private Date createdDate;
    private Date updatedDate;
}
