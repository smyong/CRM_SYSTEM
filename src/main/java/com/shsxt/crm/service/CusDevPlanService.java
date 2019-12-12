package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {


    /**
     * 添加客户开发计划
     * @param cusDevPlan
     */
    public void saveCusDevPlan(CusDevPlan cusDevPlan){
        //参数的合法性校验
        checkParams(cusDevPlan.getPlanDate(),cusDevPlan.getPlanItem(),cusDevPlan.getExeAffect());
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        //执行添加操作并验证
        AssertUtil.isTrue(save(cusDevPlan)<1,"计划项数据添加失败");
    }
    /**
     * 对计划时间、计划内容、执行效果进行非空验证
     * @param planDate
     * @param planItem
     * @param exeAffect
     */
    private void checkParams(Date planDate, String planItem, String exeAffect) {
        AssertUtil.isTrue(null==planDate,"请指定计划项时间");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划项内容!");
        AssertUtil.isTrue(StringUtils.isBlank(exeAffect),"请输入执行效果!");
    }

        /**
         * 更新客户开发数据信息
         * @param cusDevPlan
         */
        public void updateCusDevPlan(CusDevPlan cusDevPlan){
            //参数的合法性校验
            checkParams(cusDevPlan.getPlanDate(),cusDevPlan.getPlanItem(),cusDevPlan.getExeAffect());
            //设置更新时间
            cusDevPlan.setUpdateDate(new Date());
            //通过获取当前记录的ID值，判断当前记录是否存在
            AssertUtil.isTrue(null==cusDevPlan.getId()||null==queryById(cusDevPlan.getId()),"待更新记录不存在");
            //执行更新操作并判断是否操作成功
            AssertUtil.isTrue(update(cusDevPlan)<1,"计划项数据更新失败!");
        }

    /**
     * 删除客户开发数据信息
     * @param ids
     */
    public void deleteCusDevPlan(Integer[] ids){
        AssertUtil.isTrue(null==ids||ids.length==0,"请选择待删除的计划项数据");
        AssertUtil.isTrue(deleteBatch(ids)<ids.length,"计划项数据删除失败");
    }
}
