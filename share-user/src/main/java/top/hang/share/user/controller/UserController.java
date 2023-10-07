package top.hang.share.user.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.hang.share.user.domain.dto.LoginDTO;
import top.hang.share.user.domain.entity.User;
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

    @PostMapping("/login")
    private User login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }
}
