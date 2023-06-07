package com.xxr.gulimall.coupon.service.impl;

import com.xxr.common.to.SkuReductionTo;
import com.xxr.gulimall.coupon.entity.MemberPriceEntity;
import com.xxr.gulimall.coupon.entity.SkuLadderEntity;
import com.xxr.gulimall.coupon.service.MemberPriceService;
import com.xxr.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.Query;

import com.xxr.gulimall.coupon.dao.SkuFullReductionDao;
import com.xxr.gulimall.coupon.entity.SkuFullReductionEntity;
import com.xxr.gulimall.coupon.service.SkuFullReductionService;

import javax.annotation.Resource;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {
    @Resource
    SkuLadderService skuLadderService;
    @Resource
    SkuFullReductionService skuFullReductionService;
    @Resource
    MemberPriceService memberPriceService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(skuReductionTo,skuLadderEntity);
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
        skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
        if(skuReductionTo.getFullCount()>0){
            skuLadderService.save(skuLadderEntity);
        }
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo,skuFullReductionEntity);
        skuFullReductionEntity.setAddOther(skuReductionTo.getPriceStatus());
        skuFullReductionEntity.setSkuId(skuReductionTo.getSkuId());
        if(skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO)==1){
            skuFullReductionService.save(skuFullReductionEntity);
        }
        List<MemberPriceEntity> collect = skuReductionTo.getMemberPrice().stream().map(memberPrice -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId())
                    .setMemberLevelId(memberPrice.getId())
                    .setMemberLevelName(memberPrice.getName())
                    .setMemberPrice(memberPrice.getPrice())
                    .setAddOther(1);
            return memberPriceEntity;
        }).filter(item->{
            return item.getMemberPrice().compareTo(BigDecimal.ZERO)==1;
        }).collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }

}
