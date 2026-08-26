package com.ejserver.apps.ej.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;

import com.ejserver.apps.ej.bean.Product;
import com.ejserver.apps.ej.dto.ProductAndOrderLine;
import com.ejserver.apps.ej.service.IOrderLineService;
import com.ejserver.apps.ej.service.IProductService;
import com.ejserver.apps.ej.utils.ActionResult;
import com.ejserver.apps.ej.utils.ActionResultUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 张连硕
 * 2019/06/10 afternoon
 */
@RestController
@RequestMapping("product")
public class ProductController {
    @Resource
    private IProductService productService;
    @Resource
    private IOrderLineService orderLineService;


    @Operation(summary = "查询所有")
    @GetMapping("/findAll")
    public ActionResult findAll() {
        return ActionResultUtil.success("success", productService.findAll());
    }

    @Operation(summary = "通过ID查询数据")
    @GetMapping("/findById")
    public ActionResult findById(Long id) {
        return ActionResultUtil.success("success", productService.findById(id));
    }

    @Operation(summary = "通过ID删除数据")
    @PostMapping("/deleteById")
    public ActionResult deleteById(Long id) {
        try {
            productService.deleteById(id);
            return ActionResultUtil.success("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtil.error(e.getMessage());
        }

    }

    @Operation(summary = "插入数据")
    @PostMapping("/insert")
    public ActionResult insert(Product product) {
        try {
            productService.insert(product);
            return ActionResultUtil.success("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtil.error(e.getMessage());
        }
    }

    @Operation(summary = "添加或更新产品信息")
    @PostMapping("/saveOrUpdate")
    public ActionResult saveOrUpdate(Product product) {
        if (product.getId()!=null){
            try {
                productService.saveOrUpdate(product);
                return ActionResultUtil.success("success");
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtil.error(e.getMessage());
            }
        }
        return insert(product);

    }

    @Operation(summary = "通过商品Id查找商品和列表项")
    @GetMapping("/findProductAndOrderLineByProductId")
    public ActionResult findProductAndOrderLineByProductId(Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ActionResultUtil.error("id不存在");
        }
        ProductAndOrderLine productAndOrderLine = new ProductAndOrderLine();
        productAndOrderLine.setProduct(product);
        productAndOrderLine.setOrderLines(orderLineService.findByProductId(id));
        return ActionResultUtil.success("成功", productAndOrderLine);
    }

    @Operation(summary = "批量删除")
    @PostMapping("/batchDelete")
    public ActionResult batchDelete(Long[] ids) {
        try {
            productService.batchDelete(ids);
            return ActionResultUtil.success("成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtil.error("id不存在");
        }
    }
    @Operation(summary = "通过name进行模糊查询")
    @GetMapping("/findByLikeName")
    public ActionResult findByLikeName(String name){
        List<Product> products = productService.findByLikeName(name);
        return ActionResultUtil.success("成功!",products);
    }

}