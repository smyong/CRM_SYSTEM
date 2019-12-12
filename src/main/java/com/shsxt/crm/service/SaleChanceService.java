package com.shsxt.crm.service;

import com.alibaba.fastjson.serializer.ASMSerializerFactory;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {
    /**
     * 分页查询出信息
     * @param saleChanceQuery
     * @return
     */
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        PageInfo<SaleChance> pageInfo=queryForPage(saleChanceQuery);
        Map<String,Object> result=new HashMap<String, Object>();
        result.put("total",pageInfo.getTotal());
        result.put("rows",pageInfo.getList());
        return result;
    }

    /**
     * 1.判断是否添加或更新关键字段判断对象 id 值是否存在
     * 2．根据是否添加营销机会分配人判断营销机会分配状态
     * 3.对于营销机会开发状态设置 添加默认为未开发状态
     * @param saleChance
     */
    public void saveSaleChance(SaleChance saleChance){
        // 客户名  联系人  联系方式 非空校验
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //分配人默认 null  分配时间 默认 null  分配状态 默认值 0-未分配  添加时默认 未开发-0
        saleChance.setState(0);
        saleChance.setDevResult(0);
        // 如果指定分配人 设置分配时间 分配状态 1-已分配
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setAssignTime(new Date());
            saleChance.setState(1);//设置分配状态--1
            saleChance.setDevResult(1);//设置开发结果--1
        }
        saleChance.setIsValid(1);//设置有效
        saleChance.setCreateDate(new Date());//设置创建时间
        saleChance.setUpdateDate(new Date());//设置更新时间
        //执行添加并判断是否成功
        AssertUtil.isTrue(save(saleChance)<0,"营销机会添加失败");
    }
    /**
     * 对客户名、联系人、联系电话的非空校验
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"请输入客户名");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"请输入联系人");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"请输入联系人电话");
    }

    /**
     * 更新营销计划信息
     * /**
     * 1. 参数合法校验
     *     客户名  联系人  联系方式 非空校验
     *     更新记录存在性校验
     * 2.分配人
     *    如果添加时 设置分配人   更新时 没有改动分配人  不做处理
     *    如果添加时没有设置分配人 更新时设置分配人
     *          设置分配时间分配状态
     *     如果添加时 设置分配人   更新时清空分配人
     *            分配时间  分配状态
     *3. 开发状态
     *     默认未开发
     *     如果设置分配人  开发中
     * 4.updateDate
     * @param saleChance
     */
    public void updateSaleChance(SaleChance saleChance){
        //参数合法校验--客户名  联系人  联系方式 非空校验
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        // 更新记录存在性校验
        Integer sid=saleChance.getId();
        SaleChance tempSaleChance=queryById(sid);
        AssertUtil.isTrue(sid==null||null==tempSaleChance,"待更新记录不存在！");
        //判断分配人是否存在--分别设置分配时间分配状态
        if(StringUtils.isBlank(tempSaleChance.getAssignMan())&&StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(1);//设置分配状态--已分配
            saleChance.setUpdateDate(new Date());//设置更新时间
        }
        if(StringUtils.isNotBlank(tempSaleChance.getAssignMan())){//没有添加分配人
            saleChance.setState(0);//设置分配状态
            saleChance.setAssignTime(null);//设置分配时间
        }
        saleChance.setDevResult(0);//设置分配结果--未分配
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setDevResult(1);//已分配
        }
        saleChance.setUpdateDate(new Date());//设置更新时间
        //执行更新操作并判断是否更新成功
        AssertUtil.isTrue(update(saleChance)<1,"营销机会更新失败！");
    }

    /**
     * 删除营销数据信息
     * @param ids
     */
    public void deleteSaleChanceBatch(Integer []ids){
        //判断是否选择删除记录
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择待删除的数据");
        //执行删除操作并判断是否删除成功
        AssertUtil.isTrue(deleteBatch(ids)!=ids.length,"数据记录删除失败！");
    }

    /**
     * 更新机会数据开发状态
     * @param devResult
     * @param sid
     */
    public void updateSaleChanceDevResult(Integer devResult,Integer sid){
        //判断是否存在分配结果
        AssertUtil.isTrue(null==devResult,"机会数据开发状态异常！");
        //根据ID获取营销信息数据判断当前数据是否还存在
        SaleChance saleChance=queryById(sid);
        AssertUtil.isTrue(null==saleChance,"待更新的机会数据不存在！");
        //设置更新后的分配结果
        saleChance.setDevResult(devResult);
        //执行更新开发状态操作并判断是否成功
        AssertUtil.isTrue(update(saleChance)<1,"机会数据开发状态更新失败！");
    }

}

