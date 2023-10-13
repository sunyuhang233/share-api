package top.hang.share.content.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.hang.share.common.resp.CommonResp;
import top.hang.share.content.domain.dto.ShareAuditDTO;
import top.hang.share.content.domain.entity.Share;
import top.hang.share.content.service.ShareService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: AHang
 * @Date: 2023/10/13/13:30
 * @Description: 管理员相关接口
 */
@RestController
@RequestMapping("/admin/share")
public class ShareAdminController {
    @Resource
    private ShareService shareService;

    @GetMapping(value = "/list")
    public CommonResp<List<Share>> getSharesNotYet() {
        CommonResp<List<Share>> resp = new CommonResp<>();
        List<Share> shares = shareService.queryShareNotYet();
        resp.setData(shares);
        return resp;
    }

    @PostMapping(value = "/audit/{id}")
    public CommonResp<Share> auditById(@PathVariable Long id, @RequestBody ShareAuditDTO shareAuditDTO) {
        CommonResp<Share> resp = new CommonResp<>();
        resp.setData(shareService.auditById(id, shareAuditDTO));
        return resp;
    }
}
