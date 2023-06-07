package com.xxr.gulimall.product.service.impl;

import com.xxr.common.to.SkuReductionTo;
import com.xxr.common.to.SpuBoundTo;
import com.xxr.common.utils.R;
import com.xxr.gulimall.product.entity.*;
import com.xxr.gulimall.product.fegin.CouponFeignService;
import com.xxr.gulimall.product.service.*;
import com.xxr.gulimall.product.vo.Bounds;
import com.xxr.gulimall.product.vo.Images;
import com.xxr.gulimall.product.vo.SpuSaveVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxr.common.utils.PageUtils;
import com.xxr.common.utils.Query;

import com.xxr.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Resource
    SpuInfoService spuInfoService;
    @Resource
    SpuInfoDescService spuInfoDescService;
    @Resource
    SpuImagesService spuImagesService;
    @Resource
    AttrService attrService;
    @Resource
    ProductAttrValueService productAttrValueService;
    @Resource
    CouponFeignService couponFeignService;
    @Resource
    SkuInfoService skuInfoService;
    @Resource
    SkuImagesService skuImagesService;
    @Resource
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void savesupInfo(SpuSaveVo spuInfo) {
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        //spu_info  spuInfoEntity.getId()
        spuInfoService.save(spuInfoEntity);
        //spu_info_desc
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId())
                .setDecript(String.join(",",spuInfo.getDecript()));
        spuInfoDescService.save(spuInfoDescEntity);
        //spu_images
        List<SpuImagesEntity> collect = spuInfo.getImages().stream().map(images -> {
            SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
            spuImagesEntity.setSpuId(spuInfoEntity.getId());
            spuImagesEntity.setImgUrl(images);
            return spuImagesEntity;
        }).collect(Collectors.toList());
        spuImagesService.saveBatch(collect);
        // product_attr_value
        List<ProductAttrValueEntity> collect1 = spuInfo.getBaseAttrs().stream().map(attrs -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            AttrEntity byId = attrService.getById(attrs.getAttrId());

            productAttrValueEntity.setSpuId(spuInfoEntity.getId())
                    .setAttrName(byId.getAttrName())
                    .setAttrId(attrs.getAttrId())
                    .setAttrValue(attrs.getAttrValues())
                    .setQuickShow(attrs.getShowDesc());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(collect1);
        // spu_bounds
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        Bounds bounds = spuInfo.getBounds();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        R r = couponFeignService.save(spuBoundTo);
        if(r.getCode()!=0){
            log.error("spu_bounds错误");
        }
        //Skus
        spuInfo.getSkus().forEach(skus -> {
            String defaultimage="";
            for (Images image : skus.getImages()) {
                if(image.getDefaultImg()==1){
                    defaultimage=image.getImgUrl();
                }
            }
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(skus,skuInfoEntity);
            skuInfoEntity.setSpuId(spuInfoEntity.getId());
            skuInfoEntity.setSkuDefaultImg(defaultimage);
            skuInfoEntity.setCatalogId(spuInfo.getCatalogId());
            skuInfoEntity.setBrandId(spuInfo.getBrandId());
            skuInfoEntity.setSkuDesc(String.join(",",skus.getDescar()));
            //sku_info
            skuInfoService.save(skuInfoEntity);
            //sku_images
            List<SkuImagesEntity> collect2 = skus.getImages().stream().map(images -> {
                SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                BeanUtils.copyProperties(images, skuImagesEntity);
                skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                return skuImagesEntity;
            }).filter(item->{
                return StringUtils.isNotEmpty(item.getImgUrl());
            }).collect(Collectors.toList());
            skuImagesService.saveBatch(collect2);
            //sku_sale_attr_value
            List<SkuSaleAttrValueEntity> collect3 = skus.getAttr().stream().map(attr -> {
                SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                return skuSaleAttrValueEntity;
            }).collect(Collectors.toList());
            skuSaleAttrValueService.saveBatch(collect3);
            //sku_ladder,一次调用比两次调用强,coupon调用
            SkuReductionTo skuReductionTo = new SkuReductionTo();
            BeanUtils.copyProperties(skus,skuReductionTo);
            skuReductionTo.setSkuId(skuInfoEntity.getSkuId());
            R r1 = couponFeignService.saveSkuReduction(skuReductionTo);
            if(r1.getCode()!=0){
                log.error("coupon调用失败");
            }
        });
    }

}
