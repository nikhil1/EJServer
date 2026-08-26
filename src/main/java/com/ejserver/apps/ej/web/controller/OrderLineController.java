package com.ejserver.apps.ej.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;

import com.ejserver.apps.ej.bean.OrderLine;
import com.ejserver.apps.ej.service.IOrderLineService;
import com.ejserver.apps.ej.utils.ActionResult;
import com.ejserver.apps.ej.utils.ActionResultUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张连硕
 * @date 2019/06/10 afternoon
 */
@RestController
@RequestMapping("orderLine")
public class OrderLineController {
    @Resource
    private IOrderLineService orderLineService;

    @Operation(summary = "查询所有")
    @GetMapping("/findAll")
    public ActionResult findAll() {
        return ActionResultUtil.success("success", orderLineService.findAll());
    }

    @Operation(summary = "通过ID查询数据")
    @GetMapping("/findById")
    public ActionResult findById(Long id) {
        return ActionResultUtil.success("success", orderLineService.findById(id));
    }

    @Operation(summary = "通过ID删除数据")
    @PostMapping("/deleteById")
    public ActionResult deleteById(Long id) {
        try {
            orderLineService.deleteById(id);
            return ActionResultUtil.success("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtil.error(e.getMessage());
        }
        
    }

    @Operation(summary = "插入数据")
    @PostMapping("/insert")
    public ActionResult insert(OrderLine orderLine) {
        try {
            orderLineService.insert(orderLine);
            return ActionResultUtil.success("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtil.error(e.getMessage());
        }
    }

    @Operation(summary = "更新或增加生产线信息")
    @PostMapping("/saveOrUpdate")
    public ActionResult saveOrUpdate(OrderLine orderLine) {
        if (orderLine.getId()!=null){
            try {
                orderLineService.saveOrUpdate(orderLine);
                return ActionResultUtil.success("success");
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtil.error(e.getMessage());
            }
        }
        else {
            return insert(orderLine);
        }

    }

    @Operation(summary = "批量删除")
    @PostMapping("/batchDelete")
    public ActionResult batchDelete(Long[] ids) {
        try {
            orderLineService.batchDelete(ids);
            return ActionResultUtil.success("成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtil.error("id不存在");
        }
    }
    
}