package com.xxr.gulimall.product;

import com.xxr.gulimall.product.entity.BrandEntity;
import com.xxr.gulimall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBTest {
    @Autowired
    BrandService brandService;
    @Test
    public void test(){
        List<BrandEntity> r = brandService.list();
        System.out.println(r);
    }
}
