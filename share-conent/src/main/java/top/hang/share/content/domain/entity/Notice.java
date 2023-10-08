package top.hang.share.content.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : Ahang
 * @program : share-api
 * @description : Notice
 * @create : 2023-10-08 14:07
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice {
    private Long id;
    private String content;
    private Boolean showFlag;
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
