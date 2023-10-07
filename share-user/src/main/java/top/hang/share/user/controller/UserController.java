package top.hang.share.user.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.hang.share.common.resp.CommonResp;
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

    @GetMapping("/hello")
    private Long hello(){
        int a=1/0;
        return 1L;
    }

    @GetMapping("/count")
    private Long count(){
        return userService.toCount();
    }

    @PostMapping("/login")
    private CommonResp<User> login(@Valid @RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO);
        CommonResp<User> resp = new CommonResp<>();
        resp.setData(user);
        return resp;
    }
    @PostMapping("/register")
    private CommonResp<Long> register(@Valid @RequestBody LoginDTO loginDTO) {
        Long id = userService.register(loginDTO);
        CommonResp<Long> resp = new CommonResp<>();
        resp.setData(id);
        return resp;
    }
}
