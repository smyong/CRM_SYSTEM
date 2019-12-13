package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CustomerLoss;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {
    public void updateState(CustomerLoss customerLoss) {
        CustomerLoss temp = queryById(customerLoss.getId());
        AssertUtil.isTrue(null == customerLoss.getId() || null ==temp,"待流失的记录不存在!");
        customerLoss.setUpdateDate(new Date());
        customerLoss.setState(0);
        AssertUtil.isTrue(update(customerLoss)<1,"客户流失失败!");
    }
}
