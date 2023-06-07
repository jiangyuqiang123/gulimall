package com.xxr.gulimall.product.fegin;

import com.xxr.common.to.SkuReductionTo;
import com.xxr.common.to.SpuBoundTo;
import com.xxr.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @PostMapping("/coupon/spubounds/save")
    public R save(@RequestBody SpuBoundTo spuBounds);
    @RequestMapping("/coupon/skufullreduction/saveinfo")
    public R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
