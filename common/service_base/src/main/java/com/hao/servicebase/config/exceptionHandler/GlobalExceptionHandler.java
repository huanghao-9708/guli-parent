package com.hao.servicebase.config.exceptionHandler;

import com.hao.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hao   email：huanghao.0508@qq.com
 * @ClassName：GlobalExceptionHandler
 * @description ：TODO 统一全局异常处理
 * @create 2020-12-04-20:03
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//所有的异常都处理
    @ResponseBody//以json形式返回R
    public R error(Exception e){
        log.error(e.getMessage());//记录错误信息放入文件中
        e.printStackTrace();
        return R.error().msg(e.getMessage()
        );
    }
}
