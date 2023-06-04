package com.xxr.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxr.common.utils.PageUtils;
import com.xxr.gulimall.product.entity.CategoryEntity;
import com.xxr.gulimall.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:30:28
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> selectTree();

    List<CategoryEntity> getLevel1Categorys();

    Map<String, List<Catelog2Vo>> getCatalogJson();
}

