package top.hang.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.hang.share.common.resp.CommonResp;
import top.hang.share.content.domain.entity.MidUserShare;
import top.hang.share.content.domain.entity.Share;
import top.hang.share.content.domain.entity.ShareResp;
import top.hang.share.content.feign.User;
import top.hang.share.content.feign.UserService;
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

    @Resource
    private UserService userService;

    public List<Share> getList(String title,Integer pageNo,Integer pageSize, Long userId) {
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Share::getId);
        if (StringUtils.isNotEmpty(title)) {
            wrapper.like(Share::getTitle, title);
        }
        // 只显示通过的数据
        wrapper.eq(Share::getAuditStatus, "PASS").eq(Share::getShowFlag, true);
        // 配置分页对象
        Page<Share> page = Page.of(pageNo, pageSize);
        List<Share> shares = shareMapper.selectList(page,wrapper);
        List<Share> sharesDeal;
        // 如果用户Id为空 把下载地址置为空
        if (userId == null) {
            sharesDeal = shares.stream().peek(share -> share.setDownloadUrl(null)).collect(Collectors.toList());
        } else {
            // 根据用户Id查询 查询是否分享过这个链接 分享过就把下载地址默认给出 没有分享就把下载地址设置为null
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

    public ShareResp findById(Long shareId){
        Share share = shareMapper.selectById(shareId);
        CommonResp<User> commonResp = userService.getUser(share.getUserId());
        return ShareResp.builder().share(share).nickname(commonResp.getData().getNickname()).avatarUrl(commonResp.getData().getAvatarUrl()).build();
    }
}
