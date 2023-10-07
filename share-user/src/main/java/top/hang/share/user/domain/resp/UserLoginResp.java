package top.hang.share.user.domain.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.hang.share.user.domain.entity.User;

/**
 * @author : Ahang
 * @program : share-api
 * @description : UserLoginResp
 * @create : 2023-10-07 13:22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResp {
    private User user;
    private String token;
}
