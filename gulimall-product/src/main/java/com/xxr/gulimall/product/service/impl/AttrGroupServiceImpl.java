package com.xxr.gulimall.product.service.impl;

import com.xxr.gulimall.product.dao.CategoryDao;
import com.xxr.gulimall.product.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.Query;

import com.xxr.gulimall.product.dao.AttrGroupDao;
import com.xxr.gulimall.product.entity.AttrGroupEntity;
import com.xxr.gulimall.product.service.AttrGroupService;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Resource
    AttrGroupDao attrGroupDao;
    @Resource
    CategoryDao categoryDao;

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
}
