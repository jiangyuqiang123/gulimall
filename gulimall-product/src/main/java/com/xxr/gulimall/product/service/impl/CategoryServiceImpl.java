package com.xxr.gulimall.product.service.impl;

import com.xxr.gulimall.product.vo.Catelog2Vo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.Query;

import com.xxr.gulimall.product.dao.CategoryDao;
import com.xxr.gulimall.product.entity.CategoryEntity;
import com.xxr.gulimall.product.service.CategoryService;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Resource
    CategoryDao categoryDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> selectTree() {
        List<CategoryEntity> categoryEntities = categoryDao.selectTree();
        return categoryEntities;
    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>()
                .eq("parent_cid", 0));
        return categoryEntities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
//        List<CategoryEntity> level1Categorys = this.getLevel1Categorys();
        List<CategoryEntity> selectList = baseMapper.selectList(null);
        List<CategoryEntity> level1Categorys=getParent_cid(selectList,0l);
        Map<String, List<Catelog2Vo>> map=new HashMap<>();
        for (CategoryEntity level1Category : level1Categorys) {
            List<CategoryEntity> categoryEntities2 = getParent_cid(selectList,level1Category.getCatId());
            List<Catelog2Vo> catelog2Vos=new ArrayList<>();
            for (CategoryEntity categoryEntity : categoryEntities2) {
                Catelog2Vo catelog2Vo = new Catelog2Vo();
                catelog2Vo.setCatalog1Id(level1Category.getCatId().toString());
                catelog2Vo.setId(categoryEntity.getCatId().toString());
                catelog2Vo.setName(categoryEntity.getName());

                List<CategoryEntity> categoryEntities3 = getParent_cid(selectList,categoryEntity.getCatId());
                List<Catelog2Vo.Category3Vo> catelog3Vos=new ArrayList<>();
                for (CategoryEntity entity : categoryEntities3) {
                    Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo();
                    category3Vo.setCatalog2Id(categoryEntity.getCatId().toString());
                    category3Vo.setId(entity.getCatId().toString());
                    category3Vo.setName(entity.getName());
                    catelog3Vos.add(category3Vo);
                }
                catelog2Vo.setCatalog3List(catelog3Vos);
                catelog2Vos.add(catelog2Vo);
            }
            map.put(level1Category.getCatId().toString(),catelog2Vos);
        }
        return map;
    }
    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList,Long parentCid) {
        List<CategoryEntity> categoryEntities = selectList.stream().filter(item -> item.getParentCid().equals(parentCid))
                .collect(Collectors.toList());
        return categoryEntities;
    }
}
