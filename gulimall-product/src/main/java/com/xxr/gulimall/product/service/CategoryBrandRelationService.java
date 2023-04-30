package com.xxr.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxr.common.utils.PageUtils;
import com.xxr.gulimall.product.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-04-30 17:59:42
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

