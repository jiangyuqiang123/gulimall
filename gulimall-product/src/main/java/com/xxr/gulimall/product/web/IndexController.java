package com.xxr.gulimall.product.web;

import com.xxr.gulimall.product.entity.CategoryEntity;
import com.xxr.gulimall.product.service.CategoryService;
import com.xxr.gulimall.product.vo.Catelog2Vo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Resource
    CategoryService categoryService;
    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntities= categoryService.getLevel1Categorys();
        model.addAttribute("categories",categoryEntities);
        return "index";
    }
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String,List<Catelog2Vo>> getCatalogJson(){
        Map<String,List<Catelog2Vo>> map=categoryService.getCatalogJson();
        return map;
    }
}
