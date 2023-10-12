package top.hang.share.content.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: AHang
 * @Date: 2023/10/12/9:10
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareResp {
    private Share share;
    private String nickname;
    private String avatarUrl;
}
