package com.xxr.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxr.common.utils.PageUtils;
import com.xxr.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author jyq
 * @email 1843309310@qq.com
 * @date 2023-05-01 18:52:56
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

