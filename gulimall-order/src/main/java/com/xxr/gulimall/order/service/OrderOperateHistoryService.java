package com.xxr.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxr.common.utils.PageUtils;
import com.xxr.gulimall.order.entity.OrderOperateHistoryEntity;

import java.util.Map;

/**
 * 订单操作历史记录
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:54:49
 */
public interface OrderOperateHistoryService extends IService<OrderOperateHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

