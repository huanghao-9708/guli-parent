package com.hao.utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：R
 * @description ：接口统一的返回值类型
 * @create 2020-12-04-15:31
 */
@Api(description = "接口统一的返回值类型")
@Data
public class R {
    @ApiModelProperty(value = "表示请求是否成功,true表示是,false反之")
    private Boolean success;
    @ApiModelProperty(value = "表示的状态码")
    private Integer code;
    @ApiModelProperty(value = "表示请求返回的信息")
    private String msg;
    @ApiModelProperty(value = "表示请求返回的数据")
    private Map<String,Object> data;
    //私有化构造器，防止该类另做他用
    private R(){}
    @ApiOperation(value = "表示请求成功")
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMsg("请求成功");
        return r;
    }
    @ApiOperation(value = "表示请求成功")
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR.getCode());
        r.setMsg("请求失败");
        return r;
    }
    @ApiOperation(value = "设置请求返回的信息msg")
    public R msg(String msg){
        this.setMsg(msg);
        return this;
    }

    @ApiOperation(value = "添加data")
    public R data(String key,Object value){
        if (this.data!=null){
            this.data.put(key,value);
        }else {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(key,value);
            this.data=hashMap;
        }
        return this;
    }

    @ApiOperation(value = "设置data")
    public R data(Map<String,Object> data){
        this.setData(data);
        return this;
    }
    @ApiOperation(value = "code")
    public R code(Integer code){
        this.setCode(code);
        return this;
    }
}
