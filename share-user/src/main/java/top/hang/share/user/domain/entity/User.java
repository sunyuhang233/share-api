package top.hang.share.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : Ahang
 * @program : share-api
 * @description : User实体类
 * @create : 2023-10-07 10:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String phone;
    private String password;
    private String nickname;
    private String roles;
    private String avatarUrl;
    private Integer bonus;
    private Date createTime;
    private Date updateTime;
}
