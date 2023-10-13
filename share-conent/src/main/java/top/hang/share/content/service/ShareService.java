package top.hang.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import top.hang.share.common.resp.CommonResp;
import top.hang.share.content.domain.dto.ExchangeDTO;
import top.hang.share.content.domain.dto.ShareAuditDTO;
import top.hang.share.content.domain.dto.ShareRequestDTO;
import top.hang.share.content.domain.dto.UserAddBonusMQDTO;
import top.hang.share.content.domain.entity.MidUserShare;
import top.hang.share.content.domain.entity.Share;
import top.hang.share.content.domain.entity.ShareResp;
import top.hang.share.content.domain.entity.UserAddBonusMsgDTO;
import top.hang.share.content.enums.AuditStatusEnum;
import top.hang.share.content.feign.User;
import top.hang.share.content.feign.UserService;
import top.hang.share.content.mapper.MidUserShareMapper;
import top.hang.share.content.mapper.ShareMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public List<Share> myExchange(Long userId) {
        LambdaQueryWrapper<MidUserShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MidUserShare::getUserId, userId);
        List<MidUserShare> shareList = midUserShareMapper.selectList(wrapper);
        List<Long> list = shareList.stream().map(item -> item.getShareId()).collect(Collectors.toList());
        LambdaQueryWrapper<Share> queryWrapper = new LambdaQueryWrapper<>();
        List<Share> shares = new ArrayList<Share>();
        for (Long shareId : list) {
            Share share = shareMapper.selectById(shareId);
            shares.add(share);
        }
        return shares;
    }

    /***
     * @description 审核
     * @param id id
     * @param shareAuditDTO shareAuditDTO
     * @return Share
     */
    public Share auditById(Long id, ShareAuditDTO shareAuditDTO) {
        Share share = shareMapper.selectById(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法 该分享不存在");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法 该分享已审核或者审核不通过");
        }
        share.setAuditStatus(shareAuditDTO.getAuditStatusEnum().toString());
        share.setReason(shareAuditDTO.getReason());
        share.setShowFlag(shareAuditDTO.getShowFlag());
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Share::getId, id);
        this.shareMapper.update(share, wrapper);
        this.midUserShareMapper.insert(
                MidUserShare.builder()
                        .userId(share.getUserId())
                        .shareId(id)
                        .build()
        );
        if (AuditStatusEnum.PASS.equals(shareAuditDTO.getAuditStatusEnum())) {
            this.rocketMQTemplate.convertAndSend(
                    "add-bonus",
                    UserAddBonusMQDTO.builder()
                            .userId(share.getUserId())
                            .bonus(50)
                            .build()
            );
        }
        return share;
    }

    public Share exchange(ExchangeDTO exchangeDTO) {
        Long shareId = exchangeDTO.getShareId();
        Long userId = exchangeDTO.getUserId();
        Share share = shareMapper.selectById(shareId);
        if (share == null) {
            throw new IllegalArgumentException("该分享不存在");
        }
        MidUserShare userShare = midUserShareMapper.selectOne(new QueryWrapper<MidUserShare>().lambda()
                .eq(MidUserShare::getUserId, userId)
                .eq(MidUserShare::getShareId, shareId)
        );
        if (userShare != null) {
            return share;
        }

        CommonResp<User> commonResp = userService.getUser(userId);
        User user = commonResp.getData();
        Integer price = share.getPrice();
        if (price > user.getBonus()) {
            throw new IllegalArgumentException("用户积分不够");
        }
        userService.updateBonus(UserAddBonusMsgDTO.builder()
                .userId(userId)
                .bonus(price * -1)
                .build());
        midUserShareMapper.insert(MidUserShare.builder().userId(userId).shareId(shareId).build());
        return share;
    }

    public List<Share> getList(String title, Integer pageNo, Integer pageSize, Long userId) {
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Share::getId);
        if (StringUtils.isNotEmpty(title)) {
            wrapper.like(Share::getTitle, title);
        }
        // 只显示通过的数据
        wrapper.eq(Share::getAuditStatus, "PASS").eq(Share::getShowFlag, true);
        // 配置分页对象
        Page<Share> page = Page.of(pageNo, pageSize);
        List<Share> shares = shareMapper.selectList(page, wrapper);
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

    public ShareResp findById(Long shareId) {
        Share share = shareMapper.selectById(shareId);
        CommonResp<User> commonResp = userService.getUser(share.getUserId());
        return ShareResp.builder().share(share).nickname(commonResp.getData().getNickname()).avatarUrl(commonResp.getData().getAvatarUrl()).build();
    }

    public int contribute(ShareRequestDTO shareRequestDTO) {
        Share share = Share.builder()
                .isOriginal(shareRequestDTO.getIsOriginal())
                .author(shareRequestDTO.getAuthor())
                .price(shareRequestDTO.getPrice())
                .downloadUrl(shareRequestDTO.getDownloadUrl())
                .summary(shareRequestDTO.getSummary())
                .buyCount(0)
                .title(shareRequestDTO.getTitle())
                .userId(shareRequestDTO.getUserId())
                .cover(shareRequestDTO.getCover())
                .createTime(new Date())
                .updateTime(new Date())
                .showFlag(false)
                .auditStatus("NOT_YET")
                .reason("未审核")
                .build();
        return shareMapper.insert(share);
    }

    public List<Share> myContribute(Integer pageNo,
                                    Integer pageSize,
                                    Long userId
    ) {
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Share::getUserId, userId);
        Page<Share> page = Page.of(pageNo, pageSize);
        List<Share> selectList = shareMapper.selectList(page, wrapper);
        return selectList;
    }

    /***
     * @description 查询待审核状态的shares列表
     *
     * @return List<Share>
     */
    public List<Share> queryShareNotYet() {
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Share::getId);
        wrapper.eq(Share::getShowFlag, false)
                .eq(Share::getAuditStatus, "NOT_YET");
        return shareMapper.selectList(wrapper);
    }
}
