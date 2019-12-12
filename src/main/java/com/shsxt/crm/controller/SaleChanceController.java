package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.annotations.RequirePermission;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;
    @Resource
    private UserService userService;

    @RequestMapping("index")
    public String index(){
        return "sale_chance";
    }

    /**
     * 分页查询出客户开发数据信息
     * @param saleChanceQuery
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    //@RequirePermission(aclValue = "101001")
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery,
                                                      @RequestParam(defaultValue = "1") Integer page,
                                                      @RequestParam(defaultValue ="10") Integer rows,@RequestParam(defaultValue = "0") Integer flag,HttpServletRequest request){
        saleChanceQuery.setPageNum(page);
        saleChanceQuery.setPageSize(rows);
        if(flag==1){
            //设置分配人
            saleChanceQuery.setAssignMan(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    /**
     * 添加营销机会数据
     * @param request
     * @param saleChance
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    //@RequirePermission(aclValue = "101002")
    public ResultInfo saveSaleChance(HttpServletRequest request, SaleChance saleChance){
        //执行releaseUserIdFromCookie从cookie中获取信息ID
        Integer userId=LoginUserUtil.releaseUserIdFromCookie(request);
        saleChance.setCreateMan(userService.queryById(userId).getTrueName());
        saleChanceService.saveSaleChance(saleChance);
        return success("营销机会数据添加成功！");
    }
    @RequestMapping("update")
    @ResponseBody
    //@RequirePermission(aclValue = "101004")
    public ResultInfo upateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功！");
    }
    @RequestMapping("delete")
    @ResponseBody
    @RequirePermission(aclValue = "101003")
    public ResultInfo deleteSaleChance(Integer []ids){
        saleChanceService.deleteSaleChanceBatch(ids);
        return success("营销机会数据删除成功");
    }
    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer devResult,Integer sid){
        saleChanceService.updateSaleChanceDevResult(devResult,sid);
        return success("营销机会开发状态更新成功！");
    }
}
