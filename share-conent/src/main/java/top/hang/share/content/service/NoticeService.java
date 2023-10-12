package top.hang.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import top.hang.share.content.config.SystemConfig;
import top.hang.share.content.domain.entity.Notice;
import top.hang.share.content.mapper.NoticeMapper;

import java.util.List;

/**
 * @author : Ahang
 * @program : share-api
 * @description : NoticeService
 * @create : 2023-10-08 14:30
 **/
@Service
@Slf4j
@RefreshScope
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private SystemConfig systemConfig;


    /***
     * @description 通知
     *
     * @return
     */
    public Notice getLatest() {
        System.out.println(systemConfig.getSystemNoticeMessage());
        System.out.println(systemConfig.isSystemNoticeEnabled());
        if (systemConfig.isSystemNoticeEnabled()) {
            return Notice.builder()
                    .content(systemConfig.getSystemNoticeMessage())
                    .showFlag(true)
                    .build();
        } else {
            LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Notice::getShowFlag, 1);
            wrapper.orderByDesc(Notice::getId);
            List<Notice> list = noticeMapper.selectList(wrapper);
            return list.get(0);
        }
    }


}
