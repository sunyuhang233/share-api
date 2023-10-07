package top.hang.share.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Ahang
 * @program : share-api
 * @description :
 * @create : 2023-10-07 08:20
 **/
@Controller
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
