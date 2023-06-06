package com.xxr.gulimall.product.service.impl;

import com.xxr.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.xxr.gulimall.product.dao.AttrDao;
import com.xxr.gulimall.product.dao.CategoryDao;
import com.xxr.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.xxr.gulimall.product.entity.AttrEntity;
import com.xxr.gulimall.product.entity.CategoryEntity;
import com.xxr.gulimall.product.service.AttrService;
import com.xxr.gulimall.product.vo.AttVo;
import com.xxr.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.Query;

import com.xxr.gulimall.product.dao.AttrGroupDao;
import com.xxr.gulimall.product.entity.AttrGroupEntity;
import com.xxr.gulimall.product.service.AttrGroupService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Resource
    AttrGroupDao attrGroupDao;
    @Resource
    CategoryDao categoryDao;
    @Resource
    AttrDao attrDao;
    @Resource
    AttrService attrService;
    @Resource
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
            //select * from pms_attr_group where catelog_id=? and (attr_group_id=? or attr_group_name=%key%);
            String key = (String) params.get("key");
            IPage<AttrGroupEntity> page = attrGroupDao.query(
                    new Query<AttrGroupEntity>().getPage(params),
                    categoryId, key);
            return new PageUtils(page);
    }

    @Override
    public Long[] selectcatelogPath(Long categoryId) {
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
        return list.toArray(new Long[list.size()]);
    }

    @Override
    public List<AttVo> selectAttrByAttrgroupId(Long attrgroupId) {
        List<Long> ids=attrAttrgroupRelationDao.selectAttrIdsByAttrgroupId(attrgroupId);
        if(CollectionUtils.isEmpty(ids))return null;
        List<AttrEntity> attrEntities = attrDao.selectBatchId(ids);
        List<AttVo> result=new ArrayList<>();
        for (AttrEntity attrEntity : attrEntities) {
            AttVo attVo = new AttVo();
            BeanUtils.copyProperties(attrEntity,attVo);
            result.add(attVo);
        }
        return result;
    }

    @Override
    public void removeAttrAttrgroupRelationEntityList(List<AttrAttrgroupRelationEntity> list) {
        attrAttrgroupRelationDao.removeAttrAttrgroupRelationEntityList(list);
    }

    @Override
    public PageUtils queryPageNoattr(Map<String, Object> params, Long attrgroupId) {
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = attrAttrgroupRelationDao.selectList(new QueryWrapper<>());
        List<Long> list=new ArrayList<>();
        for (AttrAttrgroupRelationEntity attrAttrgroupRelationEntity : attrAttrgroupRelationEntities) {
            list.add(attrAttrgroupRelationEntity.getAttrId());
        }
        IPage<AttrEntity> page = attrDao.page(
                new Query<AttrEntity>().getPage(params),
                list,
                catelogId
        );
        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        List<AttrGroupEntity> arrGroups = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<AttrGroupWithAttrsVo> collect = arrGroups.stream().map(attrGroupEntity -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(attrGroupEntity, attrGroupWithAttrsVo);
            List<AttrEntity> attrEntities = attrService.getRelationAttr(attrGroupEntity.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrEntities);
            return attrGroupWithAttrsVo;

        }).collect(Collectors.toList());
        return collect;
    }
}
