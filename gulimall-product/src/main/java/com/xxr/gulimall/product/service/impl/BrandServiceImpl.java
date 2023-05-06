package com.xxr.gulimall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.Query;

import com.xxr.gulimall.product.dao.BrandDao;
import com.xxr.gulimall.product.entity.BrandEntity;
import com.xxr.gulimall.product.service.BrandService;

import javax.annotation.Resource;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {
    @Resource
    BrandDao brandDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key=(String)params.get("key");
        IPage<BrandEntity> page = brandDao.page(
                new Query<BrandEntity>().getPage(params),
                key
        );

        return new PageUtils(page);
    }

}
