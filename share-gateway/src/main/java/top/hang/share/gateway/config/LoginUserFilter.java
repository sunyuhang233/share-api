package top.hang.share.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.hang.share.gateway.util.JwtUtil;

/**
 * @author : Ahang
 * @program : share-api
 * @description : LoginUserFilter
 * @create : 2023-10-07 13:41
 **/
@Component
@Slf4j
public class LoginUserFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        // 白名单
        if(path.contains("/admin") || path.contains("/user-service/user/login") || path.contains("/user-service/user/register")) {
            log.info("不需要登录验证:{}", path);
            return chain.filter(exchange);
        }else {
            log.info("需要登录验证:{}",path);
        }
        // 获取header的token参数
        String token = exchange.getRequest().getHeaders().getFirst("token");
        log.info("验证开始 token:{}",token);
        if(token==null || token.isEmpty()){
            log.info("token为空 请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 校验token
        boolean validate = JwtUtil.validate(token);
        if(validate){
            log.info("token 有效 验证通过");
            return chain.filter(exchange);
        }else {
            log.warn("token无效 请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }


    /***
     * @description 优先级设置值越小 优先级越高
     *
     * @return int
    */
    @Override
    public int getOrder() {
        return 0;
    }
}
