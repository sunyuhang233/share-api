package top.hang.share.content.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: AHang
 * @Date: 2023/10/13/15:11
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    // 待审核
    NOT_YET,
    // 审核通过
    PASS,
    // 审核不通过
    REJECT
}
