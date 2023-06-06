package com.xxr.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxr.common.utils.PageUtils;
import com.xxr.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.xxr.gulimall.product.entity.AttrGroupEntity;
import com.xxr.gulimall.product.vo.AttVo;
import com.xxr.gulimall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:30:28
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params,Long categoryId);

    Long[] selectcatelogPath(Long categoryId);

    List<AttVo> selectAttrByAttrgroupId(Long attrgroupId);

    void removeAttrAttrgroupRelationEntityList(List<AttrAttrgroupRelationEntity> list);

    PageUtils queryPageNoattr(Map<String, Object> params, Long attrgroupId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}

