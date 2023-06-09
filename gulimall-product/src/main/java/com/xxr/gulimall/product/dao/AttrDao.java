package com.xxr.gulimall.product.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxr.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxr.gulimall.product.entity.AttrGroupEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品属性
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:30:28
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    IPage<AttrEntity> queryBaseAttrPage(IPage<AttrEntity> page, String catelogId, String s, String key);

    IPage<AttrEntity> page(IPage<AttrEntity> page, List<Long> list, Long catelogId);

    List<AttrEntity> selectBatchId(List<Long> ids);
}
