package top.hang.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
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
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    public Notice getLatest(){
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getShowFlag,1);
        wrapper.orderByDesc(Notice::getId);
        List<Notice> list = noticeMapper.selectList(wrapper);
        return list.get(0);
    }
}
