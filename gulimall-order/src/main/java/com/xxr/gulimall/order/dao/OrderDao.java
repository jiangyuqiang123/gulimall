package com.xxr.gulimall.order.dao;

import com.xxr.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:54:49
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
