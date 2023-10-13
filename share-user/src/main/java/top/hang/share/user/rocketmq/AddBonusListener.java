package top.hang.share.user.rocketmq;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import top.hang.share.user.domain.dto.UserAddBonusMQDTO;
import top.hang.share.user.domain.entity.BonusEventLog;
import top.hang.share.user.domain.entity.User;
import top.hang.share.user.mapper.BonusEventLogMapper;
import top.hang.share.user.mapper.UserMapper;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: AHang
 * @Date: 2023/10/13/15:58
 * @Description:
 */
@Service
@RocketMQMessageListener(consumerGroup = "consumer",topic = "add-bonus")
public class AddBonusListener implements RocketMQListener<UserAddBonusMQDTO> {
    @Resource
    private UserMapper userMapper;
    @Resource
    private BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMQDTO userAddBonusMQDTO){
        Long userId = userAddBonusMQDTO.getUserId();
        User user = userMapper.selectById(userId);
        user.setBonus(user.getBonus()+userAddBonusMQDTO.getBonus());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,userId);
        userMapper.update(user,wrapper);
        bonusEventLogMapper.insert(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(userAddBonusMQDTO.getBonus())
                        .event("CONTRIBUTE")
                        .createTime(new Date())
                        .description("投稿加积分")
                        .build()
        );
    }

}
