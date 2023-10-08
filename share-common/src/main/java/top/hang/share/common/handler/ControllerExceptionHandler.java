package top.hang.share.common.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.hang.share.common.resp.CommonResp;
import top.hang.share.common.exception.BusinessException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(Exception e) throws Exception{
        CommonResp<?> resp = new CommonResp<>();
        log.error("系统异常",e);
        resp.setSuccess(false);
        resp.setMessage(e.getMessage());
        return resp;
    }

    /**
     * 业务异常统一处理
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(BusinessException e) {
        CommonResp<?> commonResp = new CommonResp<>();
        log.error("业务异常:", e);
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getE().getDesc());
        return commonResp;
    }
    /**
     * 数据校验统一处理
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(BindException e) {
        CommonResp<?> commonResp = new CommonResp<>();
        log.error("异常校验:{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }
}
