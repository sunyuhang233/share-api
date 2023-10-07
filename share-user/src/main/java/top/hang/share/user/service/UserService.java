package top.hang.share.user.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.hang.share.common.exception.BusinessException;
import top.hang.share.common.exception.BusinessExceptionEnum;
import top.hang.share.common.util.SnowUtil;
import top.hang.share.user.domain.dto.LoginDTO;
import top.hang.share.user.domain.entity.User;
import top.hang.share.user.domain.resp.UserLoginResp;
import top.hang.share.user.mapper.UserMapper;

import java.util.Date;
import java.util.Map;

/**
 * @author : Ahang
 * @program : share-api
 * @description : UserService
 * @create : 2023-10-07 10:43
 **/
@Service
public class UserService  {
    @Resource
    private UserMapper userMapper;

    public Long toCount(){
        return userMapper.selectCount(null);
    }

    public UserLoginResp login(LoginDTO loginDTO){
        // 查询用户
        User userDb = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, loginDTO.getPhone()));
        // 没有找到抛出异常
        if(userDb == null){
            throw new BusinessException(BusinessExceptionEnum.PHONE_NOT_EXIST);
        }
        // 校验密码
        if(!userDb.getPassword().equals(loginDTO.getPassword())){
            throw new BusinessException(BusinessExceptionEnum.PASSWORD_ERROR);
        }
        // 都正确返回
        UserLoginResp loginResp = new UserLoginResp();
        loginResp.setUser(userDb);
        String key="AHang";
        Map<String, Object> beanToMap = BeanUtil.beanToMap(loginResp);
        String token = JWTUtil.createToken(beanToMap, key.getBytes());
        loginResp.setToken(token);
        return loginResp;
    }

    public Long register(LoginDTO loginDTO){
        // 查询用户
        User userDb = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, loginDTO.getPhone()));
        // 找到抛出异常
        if(userDb != null){
            throw new BusinessException(BusinessExceptionEnum.PHONE_EXIST);
        }
        User user = User.builder()
                .id(SnowUtil.getSnowflakeNextId())
                .phone(loginDTO.getPhone())
                .password(loginDTO.getPassword())
                .nickname("新用户")
                .roles("user")
                .avatarUrl("https://i2.100024.xyz/2023/01/26/3e727b.webp")
                .bonus(100)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        // 都正确返回
        userMapper.insert(user);
        return user.getId();
    }
}
