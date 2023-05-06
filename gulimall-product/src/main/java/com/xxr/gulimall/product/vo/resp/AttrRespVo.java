package com.xxr.gulimall.product.vo.resp;

import com.xxr.gulimall.product.vo.AttVo;
import lombok.Data;

@Data
public class AttrRespVo extends AttVo {
    private String catelogName;

    private String groupName;
    private Long[] catelogPath;
}
