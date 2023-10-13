package top.hang.share.content.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Share> getSharesNotYet() {
        return shareService.queryShareNotYet();
    }
}
