package com.xxr.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxr.common.utils.PageUtils;
import com.xxr.gulimall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:30:28
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateByBrandId(CategoryBrandRelationEntity categoryBrandRelationEntity);

    void updateByCatelogId(CategoryBrandRelationEntity categoryBrandRelationEntity);

    List<CategoryBrandRelationEntity> queryPageCatId(Map<String, Object> params);


//    PageUtils queryPageCatId(Map<String, Object> params);
}

