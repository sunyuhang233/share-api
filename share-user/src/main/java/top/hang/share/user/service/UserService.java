package top.hang.share.user.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
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
}
