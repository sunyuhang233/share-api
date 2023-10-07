package top.hang.share.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.hang.share.common.exception.BusinessException;
import top.hang.share.common.resp.CommonResp;

/**
 * @author : Ahang
 * @program : share-api
 * @description : ControllerExceptionHandler
 * @create : 2023-10-07 12:38
 **/
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /***
     * @description 系统异常
     * @param e
     * @return CommonResp
    */
    @ExceptionHandler(value= Exception.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(Exception e) throws Exception{
        CommonResp<Object> resp = new CommonResp<>();
        log.error("系统异常",e);
        resp.setSuccess(false);
        resp.setMessage(e.getMessage());
        return resp;
    }
    /***
     * @description 业务异常
     * @param e
     * @return CommonResp
     */
    @ExceptionHandler(value= BusinessException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(BusinessException e) {
        CommonResp<?> resp = new CommonResp<>();
        log.error("业务异常",e);
        resp.setSuccess(false);
       resp.setMessage(e.getE().getDesc());
        return resp;
    }
}
