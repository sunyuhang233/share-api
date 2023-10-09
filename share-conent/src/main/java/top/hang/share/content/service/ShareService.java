package top.hang.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.hang.share.content.domain.entity.MidUserShare;
import top.hang.share.content.domain.entity.Share;
import top.hang.share.content.mapper.MidUserShareMapper;
import top.hang.share.content.mapper.ShareMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: AHang
 * @Date: 2023/10/08/17:01
 * @Description:
 */
@Service
@Slf4j
public class ShareService {
    @Resource
    private MidUserShareMapper midUserShareMapper;
    @Resource
    private ShareMapper shareMapper;

    public List<Share> getList(String title,Integer pageNo,Integer pageSize, Long userId) {
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Share::getId);
        if (StringUtils.isNotEmpty(title)) {
            wrapper.like(Share::getTitle, title);
        }
        wrapper.eq(Share::getAuditStatus, "PASS").eq(Share::getShowFlag, true);
        // 配置分页对象
        Page<Share> page = Page.of(pageNo, pageSize);
        List<Share> shares = shareMapper.selectList(page,wrapper);
        List<Share> sharesDeal;
        if (userId == null) {
            sharesDeal = shares.stream().peek(share -> share.setDownloadUrl(null)).collect(Collectors.toList());
        } else {
            sharesDeal = shares.stream().peek(share -> {
                MidUserShare midUserShare = midUserShareMapper.selectOne(new QueryWrapper<MidUserShare>().lambda()
                        .eq(MidUserShare::getUserId, userId)
                        .eq(MidUserShare::getShareId, share.getId()));
                if (midUserShare == null) {
                    share.setDownloadUrl(null);
                }
            }).collect(Collectors.toList());
        }
        return sharesDeal;
    }
}
