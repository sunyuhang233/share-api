package top.hang.share.content.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.hang.share.common.resp.CommonResp;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: AHang
 * @Date: 2023/10/12/9:07
 * @Description:
 */
@FeignClient(value = "user-service", path = "/user")
public interface UserService {

    /***
     * @description 调用用户中心查询用户信息接口
     * @param id 用户id
     * @return CommonResp<User>
     */
    @GetMapping("/{id}")
    CommonResp<User> getUser(@PathVariable Long id);
}
