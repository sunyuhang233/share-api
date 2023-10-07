package top.hang.share.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author : Ahang
 * @program : share-api
 * @description : 网关启动类
 * @create : 2023-10-07 10:30
 **/
@SpringBootApplication
@ComponentScan("top.hang")
@Slf4j
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        log.info("启动成功");
        log.info("测试地址:http://127.0.0.1:{}",env.getProperty("server.port"));
    }
}
