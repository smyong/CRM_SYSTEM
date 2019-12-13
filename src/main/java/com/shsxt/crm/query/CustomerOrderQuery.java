package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

public class CustomerOrderQuery extends BaseQuery {
    private Integer cusId;
    private String orderNo;
    private Integer state;

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
