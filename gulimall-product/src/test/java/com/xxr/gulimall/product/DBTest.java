package com.xxr.gulimall.product;

import com.xxr.gulimall.product.entity.BrandEntity;
import com.xxr.gulimall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBTest {
    @Autowired
    BrandService brandService;
    @Test
    public void test(){
        String[] arr=new String[]{"12","33","44"};
        Stream<String> stream=Stream.of(arr);
        Stream<Integer> stream2 = stream.map(s -> Integer.parseInt(s));
        stream2.forEach(s -> System.out.println(s));
    }
}
