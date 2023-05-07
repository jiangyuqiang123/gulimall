package com.xxr.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xxr.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.xxr.gulimall.product.service.AttrAttrgroupRelationService;
import com.xxr.gulimall.product.vo.AttVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxr.gulimall.product.entity.AttrGroupEntity;
import com.xxr.gulimall.product.service.AttrGroupService;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.R;

import javax.annotation.Resource;


/**
 * 属性分组
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:30:28
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;
    /**
     * /product/attrgroup/{attrgroupId}/attr/relation
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R list1(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttVo> attVoList=attrGroupService.selectAttrByAttrgroupId(attrgroupId);
        return R.ok().put("data",attVoList);
    }
    /**
     * /product/attrgroup/attr/relation
     */
    @PostMapping("/attr/relation")
    public R saveBath(@RequestBody List<AttrAttrgroupRelationEntity> attrGroup) {
        attrAttrgroupRelationService.saveBatch(attrGroup);
        return R.ok();
    }
    /**
     * /product/attrgroup/attr/relation/delete
     * @param
     * @param
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R delete1(@RequestBody List<AttrAttrgroupRelationEntity> list) {
        attrGroupService.removeAttrAttrgroupRelationEntityList(list);
        return R.ok();
    }
    /**
     * /product/attrgroup/{attrgroupId}/noattr/relation
     */
    @RequestMapping("/{attrgroupId}/noattr/relation")
    public R noattr(@RequestParam Map<String, Object> params,
                  @PathVariable("attrgroupId") Long attrgroupId) {
        PageUtils page = attrGroupService.queryPageNoattr(params, attrgroupId);
        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list/{categoryId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("categoryId") Long categoryId) {
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, categoryId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] catelogPath = attrGroupService.selectcatelogPath(catelogId);
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
