package top.hang.share.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.hang.share.user.domain.dto.LoginDTO;
import top.hang.share.user.domain.entity.User;
import top.hang.share.user.mapper.UserMapper;

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

    public User login(LoginDTO loginDTO){
        // 查询用户
        User userDb = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, loginDTO.getPhone()));
        // 没有找到抛出异常
        if(userDb == null){
            throw new RuntimeException("手机号不存在");
        }
        // 校验密码
        if(!userDb.getPassword().equals(loginDTO.getPassword())){
            throw new RuntimeException("密码不正确");
        }
        // 都正确返回
        return userDb;
    }
}
