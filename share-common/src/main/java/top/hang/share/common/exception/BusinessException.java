package top.hang.share.common.exception;

/**
 * @author : Ahang
 * @program : share-api
 * @description : BusinessException
 * @create : 2023-10-07 12:47
 **/

public class BusinessException extends RuntimeException{
        private BusinessExceptionEnum E;
    public BusinessException(BusinessExceptionEnum e){
        this.E=e;
    }
    public void setE(BusinessExceptionEnum e){
        this.E=e;
    }

    public BusinessExceptionEnum getE() {
        return E;
    }

    @Override
    public Throwable fillInStackTrace(){
        return this;
    }
}
