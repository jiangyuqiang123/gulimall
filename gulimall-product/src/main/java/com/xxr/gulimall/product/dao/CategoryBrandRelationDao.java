package com.xxr.gulimall.product.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxr.gulimall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 品牌分类关联
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:30:28
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    IPage<CategoryBrandRelationEntity> page(IPage<CategoryBrandRelationEntity> page, String brandId);

    void updateByBrandId(CategoryBrandRelationEntity categoryBrandRelationEntity);

    void updateByCatelogId(CategoryBrandRelationEntity categoryBrandRelationEntity);

    List<CategoryBrandRelationEntity> selectByCatId(String catId);
}
