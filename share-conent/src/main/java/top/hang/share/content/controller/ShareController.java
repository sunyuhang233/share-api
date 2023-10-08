package top.hang.share.content.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.hang.share.common.resp.CommonResp;
import top.hang.share.content.domain.entity.Notice;
import top.hang.share.content.service.NoticeService;

/**
 * @author : Ahang
 * @program : share-api
 * @description : ShareController
 * @create : 2023-10-08 14:32
 **/
@RestController
@RequestMapping(value = "/share")
public class ShareController {
    @Resource
    private NoticeService noticeService;

    @GetMapping(value = "/notice")
    public CommonResp<Notice> getLatestNotice(){
        CommonResp<Notice> resp = new CommonResp<>();
        Notice notice = noticeService.getLatest();
        resp.setData(notice);
        return resp;
    }
}
