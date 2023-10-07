package top.hang.share.common.exception;

import lombok.Getter;

/**
 * @author : Ahang
 * @program : share-api
 * @description : BusinessExceptionEnum
 * @create : 2023-10-07 12:44
 **/
@Getter
public enum BusinessExceptionEnum {
    PHONE_NOT_EXIST("手机号不存在"),
    PHONE_EXIST("手机号已经被注册"),
    PASSWORD_ERROR("密码错误");


    private String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BusinessExceptionEnum{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
