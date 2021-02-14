package com.hao.utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：ResultCode
 * @description ：是一个展示成功或者失败的枚举。SUCCESS就是成功，代码是20000，ERROR就是失败，代码是20001
 * @create 2020-12-04-15:25
 */
@Api("是一个展示成功或者失败的枚举。SUCCESS就是成功，代码是20000，ERROR就是失败，代码是20001")
public enum ResultCode {
    SUCCESS("SUCCESS",20000),
    ERROR("ERROR",20001);
    private String name;
    private Integer code;

    ResultCode(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
