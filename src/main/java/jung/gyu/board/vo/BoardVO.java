package jung.gyu.board.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BoardVO {
    @JsonProperty("bNo")
    private int bNo;            // 번호

    @JsonProperty("bCategory")
    private String bCategory;   // 카테고리

    @JsonProperty("bTitle")
    private String bTitle;      // 제목

    @JsonProperty("bContent")
    private String bContent;    // 내용

    @JsonProperty("bCreatedId")
    private String bCreatedId;  // 작성자

    @JsonProperty("bCreatedDate")
    private Date bCreatedDate;  // 생성일

    @JsonProperty("bUpdateDate")
    private Date bUpdateDate;   // 수정일

    @JsonProperty("bViews")
    private int bViews;         // 조회수
}
