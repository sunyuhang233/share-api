package top.hang.share.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String role;
    private String avatarUrl;
    private Integer bonus;
    private Data createTime;
    private Data updateTime;
}
