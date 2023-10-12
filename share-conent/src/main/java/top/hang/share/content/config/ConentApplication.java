package top.hang.share.content.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author : Ahang
 * @program : share-api
 * @description :
 * @create : 2023-10-08 14:01
 **/
@SpringBootApplication
@ComponentScan("top.hang")
@Slf4j
@MapperScan("top.hang.share.*.mapper")
@EnableFeignClients(basePackages = {"top.hang"})
public class ConentApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConentApplication.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();
        log.info("启动成功");
        log.info("测试地址:http://127.0.0.1:{}{}/hello",env.getProperty("server.port"),env.getProperty("server.servlet.context-path"));
    }
}
