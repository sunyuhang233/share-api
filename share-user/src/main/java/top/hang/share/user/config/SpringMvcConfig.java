package top.hang.share.user.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.hang.share.common.interceptor.LogInterceptor;

/**
 * @author : Ahang
 * @program : share-api
 * @description : SpringMvcConfig
 * @create : 2023-10-08 13:51
 **/
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Resource
    private LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(logInterceptor);
    }
}
