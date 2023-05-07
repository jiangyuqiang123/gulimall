package com.xxr.gulimall.product.service.impl;

import com.xxr.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.xxr.gulimall.product.dao.AttrGroupDao;
import com.xxr.gulimall.product.dao.CategoryDao;
import com.xxr.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.xxr.gulimall.product.entity.AttrGroupEntity;
import com.xxr.gulimall.product.entity.CategoryEntity;
import com.xxr.gulimall.product.vo.AttVo;
import com.xxr.gulimall.product.vo.resp.AttrRespVo;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.Query;

import com.xxr.gulimall.product.dao.AttrDao;
import com.xxr.gulimall.product.entity.AttrEntity;
import com.xxr.gulimall.product.service.AttrService;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    @Resource
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Resource
    AttrDao attrDao;
    @Resource
    AttrGroupDao attrGroupDao;
    @Resource
    CategoryDao categoryDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(AttVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.save(attrEntity);
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId()).setAttrId(attrEntity.getAttrId());
        attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, String catelogId, String type) {
        String key =(String) params.get("key");
        IPage<AttrEntity> page = attrDao.queryBaseAttrPage(
                new Query<AttrEntity>().getPage(params),
                catelogId,
                "base".equalsIgnoreCase(type)?"1":"0",
                key
        );
        List<AttrEntity> records = page.getRecords();
        ArrayList<AttrRespVo> list = new ArrayList<>();
        for (AttrEntity record : records) {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(record, attrRespVo);
            list.add(attrRespVo);
        }
        //todo
        for (AttrRespVo attrRespVo : list) {
            String groupId = attrAttrgroupRelationDao.selectGroupIdByAttrId(attrRespVo.getAttrId());
            if(StringUtils.isEmpty(groupId)){
                continue;
            }
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(groupId);
            if(attrGroupEntity!=null){
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(list);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = attrDao.selectById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,attrRespVo);
        String groupId = attrAttrgroupRelationDao.selectGroupIdByAttrId(attrEntity.getAttrId());
        if(StringUtils.isNotEmpty(groupId)){
            attrRespVo.setAttrGroupId(Long.valueOf(groupId));
        }
        //long[]
        Long categoryId = attrRespVo.getCatelogId();
        ArrayList<Long> list = new ArrayList<>();
        list.add(categoryId);
        CategoryEntity categoryEntity = categoryDao.selectById(categoryId);
        if (categoryEntity.getParentCid() != 0) {
            list.add(categoryEntity.getParentCid());
            CategoryEntity categoryEntity1 = categoryDao.selectById(categoryEntity.getParentCid());
            if (categoryEntity1.getParentCid() != 0) {
                list.add(categoryEntity1.getParentCid());
            }
        }
        Collections.reverse(list);
        attrRespVo.setCatelogPath(list.toArray(new Long[list.size()]));
        return attrRespVo;
    }

    @Override
    public void updateDetail(AttVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        attrAttrgroupRelationDao.updateOrSave(attr.getAttrId(),attr.getAttrGroupId());
    }
}
