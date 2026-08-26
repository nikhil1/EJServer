package com.ejserver.apps.ej.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;

import com.ejserver.apps.ej.bean.Category;
import com.ejserver.apps.ej.bean.CategoryExample;
import com.ejserver.apps.ej.bean.Product;
import com.ejserver.apps.ej.dto.CategoryAndProduct;
import com.ejserver.apps.ej.service.ICategoryService;
import com.ejserver.apps.ej.service.IProductService;
import com.ejserver.apps.ej.utils.ActionResult;
import com.ejserver.apps.ej.utils.ActionResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李洋
 * @date 2019/06/10 afternoon
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private ICategoryService categoryService;
    @Resource
    private IProductService productService;

    @Operation(summary = "插入数据")
    @PostMapping("/insert")
    public ActionResult insert(Category category){
        try{
            categoryService.insert(category);
            return ActionResultUtil.success("插入成功!");
        }catch (Exception e){
            return ActionResultUtil.error("插入失败!"+e.getMessage());
        }
    }

    @Operation(summary = "通过ID删除数据")
    @PostMapping("/deleteByPrimaryKey")
    public ActionResult deleteByPrimaryKey(Long id){
        try{
            categoryService.deleteByPrimaryKey(id);
            return ActionResultUtil.success("删除成功!");
        }catch (Exception e){
            return ActionResultUtil.error("删除失败!");
        }
    }

    @Operation(summary = "通过ID修改数据")
    @PostMapping("/updateByPrimaryKey")
    public ActionResult updateByPrimaryKey(Category category){
        try{
            categoryService.updateByPrimaryKey(category);
            return ActionResultUtil.success("更新成功!");
        }catch (Exception e){
            return ActionResultUtil.error("更新失败!");
        }
    }
    @Operation(summary = "查询所有")
    @GetMapping("/selectByExample")
    public ActionResult selectByExample(){
        List<Category> categories = categoryService.selectByExample(new CategoryExample());
        return ActionResultUtil.success("查询成功!",categories);
    }

    @Operation(summary = "通过ID查询数据")
    @GetMapping("/selectByPrimaryKey")
    public ActionResult selectByPrimaryKey(Long id) {
        Category category = categoryService.selectByPrimaryKey(id);
        if (category == null) {
            return ActionResultUtil.error("查询不存在");
        }
        return ActionResultUtil.success("查询成功", category);
    }
    @Operation(summary = "通过categoryId找到分类（category）和产品（product）的信息")
    @GetMapping("/findCategoryAndProductByCategoryId")
    public ActionResult findCategoryAndProductByCategoryId(Long categoryId){
        Category category = categoryService.selectByPrimaryKey(categoryId);
        if (category==null){
            return ActionResultUtil.error("id不存在");
        }
        List<Product> products = productService.findByCategoryId(categoryId);
        CategoryAndProduct categoryAndProduct = new CategoryAndProduct();
        categoryAndProduct.setCategory(category);
        categoryAndProduct.setProducts(products);
        return ActionResultUtil.success("成功!",categoryAndProduct);
    }

    @Operation(summary = "批量删除Category")
    @PostMapping("/batchDelete")
    public ActionResult batchDelete(Long[] ids){
        if (ids==null){
            return ActionResultUtil.error("失败!");
        }
        try {
            categoryService.batchDelete(ids);
            return ActionResultUtil.success("成功!");
        } catch (Exception e) {
            return ActionResultUtil.error("失败!");
        }
    }

    @Operation(summary = "更新或增加分类信息")
    @PostMapping("/saveOrUpdate")
    public ActionResult saveOrUpdate(Category category){
        if (category.getId()!=null){
            return updateByPrimaryKey(category);
        }else {
            return insert(category);
        }
    }


    @Operation(summary = "")
    @GetMapping("/query")
    public ActionResult query(String queryString) {
        return ActionResultUtil.success("查询成功",categoryService.query(queryString));
    }
}
