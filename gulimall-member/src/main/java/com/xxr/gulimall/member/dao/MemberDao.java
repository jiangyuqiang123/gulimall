package com.xxr.gulimall.member.dao;

import com.xxr.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:52:56
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
