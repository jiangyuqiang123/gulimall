package com.xxr.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxr.common.utils.PageUtils;
import com.xxr.gulimall.product.entity.AttrEntity;
import com.xxr.gulimall.product.vo.AttVo;
import com.xxr.gulimall.product.vo.resp.AttrRespVo;

import java.util.Map;

/**
 * 商品属性
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:30:28
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(AttVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, String catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateDetail(AttVo attr);
}

