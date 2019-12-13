package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CustomerReprieve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerReprieveService extends BaseService<CustomerReprieve,Integer> {

    /**
     * 添加措施
     * @param customerReprieve
     */
    public void saveCustomerReprieve(CustomerReprieve customerReprieve){
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()),"请输入暂缓措施内容！");
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(save(customerReprieve)<1,"措施添加失败!");
    }

    /**
     * 更新措施
     * @param customerReprieve
     */
    public void updateCustomerReprieve(CustomerReprieve customerReprieve){
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()),"请输入暂缓措施内容！");
        CustomerReprieve temp = queryById(customerReprieve.getId());
        AssertUtil.isTrue(null == customerReprieve.getId() || null ==temp,"待更新的记录不存在!");
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(update(customerReprieve)<1,"措施更新失败!");
    }

    /**
     * 删除措施
     * @param id
     */
    public void deleteCustomerReprieve(Integer id){
        CustomerReprieve temp = queryById(id);
        AssertUtil.isTrue(null == id|| null ==temp,"待删除的记录不存在!");
        temp.setIsValid(0);
        AssertUtil.isTrue(update(temp)<1,"措施删除失败!");
    }
}
