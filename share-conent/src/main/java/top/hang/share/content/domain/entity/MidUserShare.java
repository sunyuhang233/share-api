package top.hang.share.content.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Ahang
 * @program : share-api
 * @description : MidUserShare
 * @create : 2023-10-08 16:53
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MidUserShare {
    private Long id;
    private Long shareId;
    private Long userId;
}
