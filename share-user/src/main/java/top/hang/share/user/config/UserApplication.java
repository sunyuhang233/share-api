package top.hang.share.user.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : Ahang
 * @program : share-api
 * @description : user模块启动主类
 * @create : 2023-10-07 08:20
 **/
@SpringBootApplication
@ComponentScan("top.hang")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
