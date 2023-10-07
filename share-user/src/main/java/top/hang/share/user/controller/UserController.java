package top.hang.share.user.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.hang.share.user.service.UserService;

/**
 * @author : Ahang
 * @program : share-api
 * @description : UserController
 * @create : 2023-10-07 10:44
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/count")
    private Long count(){
        return userService.toCount();
    }
}
