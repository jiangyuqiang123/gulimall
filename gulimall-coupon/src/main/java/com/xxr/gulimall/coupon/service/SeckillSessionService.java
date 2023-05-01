package com.xxr.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxr.common.utils.PageUtils;
import com.xxr.gulimall.coupon.entity.SeckillSessionEntity;

import java.util.Map;

/**
 * 秒杀活动场次
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:50:34
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

