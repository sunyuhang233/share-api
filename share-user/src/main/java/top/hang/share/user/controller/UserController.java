package top.hang.share.user.controller;


import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.hang.share.common.resp.CommonResp;
import top.hang.share.user.domain.dto.LoginDTO;
import top.hang.share.user.domain.dto.UserAddBonusMsgDTO;
import top.hang.share.user.domain.entity.BonusEventLog;
import top.hang.share.user.domain.entity.User;
import top.hang.share.user.domain.resp.UserLoginResp;
import top.hang.share.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/count")
    public CommonResp<Long> count() {
        Long count = userService.count();
        CommonResp<Long> commonResp = new CommonResp<>();
        commonResp.setData(count);
        return commonResp;
    }

    @PostMapping("/login")
    public CommonResp<UserLoginResp> login(@Valid @RequestBody LoginDTO loginDTO) {
        UserLoginResp userLoginResp = userService.login(loginDTO);
        CommonResp<UserLoginResp> commonResp = new CommonResp<>();
        commonResp.setData(userLoginResp);
        return commonResp;
    }

    @PostMapping("/register")
    public CommonResp<Long> register(@Valid @RequestBody LoginDTO loginDTO) {
        Long id = userService.register(loginDTO);
        CommonResp<Long> commonResp = new CommonResp<>();
        commonResp.setData(id);
        return commonResp;
    }

    @GetMapping("/{id}")
    public CommonResp<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        CommonResp<User> resp = new CommonResp<>();
        resp.setData(user);
        return resp;
    }

    @PutMapping(value = "/update-bonus")
    public CommonResp<User> updateBonus(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO) {
        Long userId = userAddBonusMsgDTO.getUserId();
        userService.updateBonus(
                UserAddBonusMsgDTO.builder()
                        .userId(userId)
                        .bonus(userAddBonusMsgDTO.getBonus())
                        .description("兑换分享")
                        .event("BUY")
                        .build()
        );
        CommonResp<User> resp = new CommonResp<>();
        resp.setData(userService.findById(userId));
        return resp;
    }

    @GetMapping(value = "/getBonus")
    public CommonResp<List<BonusEventLog>> getBonusEventLogs(@RequestParam Long id) {
        List<BonusEventLog> list = userService.getBonusEventLog(+id);
        CommonResp<List<BonusEventLog>> resp = new CommonResp<>();
        resp.setData(list);
        return resp;
    }



}
