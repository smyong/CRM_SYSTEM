package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

public class SaleChanceQuery extends BaseQuery {

    private String customerName;//客户名称
    private Integer state;//分配状态
    private  String createMan;//创建人

    private Integer assignMan;//分配人

    public Integer getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(Integer assignMan) {
        this.assignMan = assignMan;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }
}
