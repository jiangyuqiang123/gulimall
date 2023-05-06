package com.xxr.gulimall.product.service.impl;

import com.xxr.gulimall.product.dao.BrandDao;
import com.xxr.gulimall.product.dao.CategoryDao;
import com.xxr.gulimall.product.entity.BrandEntity;
import com.xxr.gulimall.product.entity.CategoryEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.Query;

import com.xxr.gulimall.product.dao.CategoryBrandRelationDao;
import com.xxr.gulimall.product.entity.CategoryBrandRelationEntity;
import com.xxr.gulimall.product.service.CategoryBrandRelationService;

import javax.annotation.Resource;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Resource
    CategoryBrandRelationDao categoryBrandRelationDao;
    @Resource
    BrandDao brandDao;
    @Resource
    CategoryDao categoryDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String brandId = (String)params.get("brandId");
        IPage<CategoryBrandRelationEntity> page = categoryBrandRelationDao.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                brandId
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        categoryBrandRelationDao.insert(categoryBrandRelation);
    }

    @Override
    public void updateByBrandId(CategoryBrandRelationEntity categoryBrandRelationEntity) {
        categoryBrandRelationDao.updateByBrandId(categoryBrandRelationEntity);
    }

    @Override
    public void updateByCatelogId(CategoryBrandRelationEntity categoryBrandRelationEntity) {
        categoryBrandRelationDao.updateByCatelogId(categoryBrandRelationEntity);
    }

}
