package com.xxr.gulimall.product.exception;

import com.xxr.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class GulimallExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e){
        log.info("出现问题:{},异常类型:{}",e.getMessage(),e.getClass());
        HashMap<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(item->{
            map.put(item.getField(),item.getDefaultMessage());
        });
        return R.error(400,"参数异常").put("data",map);
    }
}
