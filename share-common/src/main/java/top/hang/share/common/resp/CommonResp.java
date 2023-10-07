package top.hang.share.common.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Ahang
 * @program : share-api
 * @description : CommonResp
 * @create : 2023-10-07 12:33
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResp<T> {
    private Boolean success=true;
    private String message;
    private T data;
}
