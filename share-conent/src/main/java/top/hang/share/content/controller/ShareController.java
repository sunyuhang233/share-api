package top.hang.share.content.controller;

import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.hang.share.common.resp.CommonResp;
import top.hang.share.common.util.JwtUtil;
import top.hang.share.content.domain.entity.Notice;
import top.hang.share.content.domain.entity.Share;
import top.hang.share.content.service.NoticeService;
import top.hang.share.content.service.ShareService;

import java.util.List;

/**
 * @author : Ahang
 * @program : share-api
 * @description : ShareController
 * @create : 2023-10-08 14:32
 **/
@RestController
@RequestMapping(value = "/share")
@Slf4j
public class ShareController {
    @Resource
    private NoticeService noticeService;

    @Resource
    private ShareService shareService;

    private final int MAX=100;

    @GetMapping("/list")
    public CommonResp<List<Share>> getShareList(@RequestParam(required = false) String title,
                                                @RequestParam(required = false,defaultValue = "1")Integer pageNo,
                                                @RequestParam(required = false,defaultValue = "3")Integer pageSize,
                                                @RequestHeader(value="token",required = false) String token
                                                ){
        if(pageSize> MAX){
            pageSize=MAX;
        }
        long userId= getUserIdFromToken(token);
        CommonResp<List<Share>> resp = new CommonResp<>();
        List<Share> shareList = shareService.getList(title, pageNo,pageSize,userId);
        resp.setData(shareList);
        return resp;
    }

    private long getUserIdFromToken(String token) {
        log.info(">>>>>>>token",token);
        long userId=0;
        String noToken="no-token";
        if(!noToken.equals(token)){
            JSONObject jsonObject = JwtUtil.getJSONObject(token);
            log.info("解析token的数据为:{}",jsonObject);
            userId=Long.parseLong(jsonObject.get("id").toString());
        }else {
            log.info("没有token");
        }
        return userId;
    }

    @GetMapping(value = "/notice")
    public CommonResp<Notice> getLatestNotice(){
        CommonResp<Notice> resp = new CommonResp<>();
        Notice notice = noticeService.getLatest();
        resp.setData(notice);
        return resp;
    }
}
