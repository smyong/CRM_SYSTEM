package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.CustomerOrder;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {
    /**
     * 通过客户ID查询最后一条订单记录
     * @param cusId
     * @return
     */
    public CustomerOrder queryLastCustomerOrderByCusId(Integer cusId);
}