package top.hang.share.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Ahang
 * @program : share-api
 * @description : LoginDTO
 * @create : 2023-10-07 12:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    private String phone;
    private String password;
}
