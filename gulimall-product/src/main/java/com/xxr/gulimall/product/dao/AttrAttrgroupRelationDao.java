package com.xxr.gulimall.product.dao;

import com.xxr.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:30:28
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    String selectGroupIdByAttrId(Long attrId);

    void updateOrSave(Long attrId, Long attrGroupId);

    List<Long> selectAttrIdsByAttrgroupId(Long attrgroupId);

    void removeAttrAttrgroupRelationEntityList(List<AttrAttrgroupRelationEntity> list);
}
