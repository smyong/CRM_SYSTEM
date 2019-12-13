package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.query.CustomerOrderQuery;
import com.shsxt.crm.service.CustomerOrderServcie;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_order")
public class CustomerOrderController extends BaseController {

    @Resource
    private CustomerOrderServcie customerOrderServcie;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerOrderByParams(CustomerOrderQuery customerOrderQuery,
                                                         @RequestParam(defaultValue = "1")Integer page,
                                                         @RequestParam(defaultValue = "10")Integer rows){
        customerOrderQuery.setPageNum(page);
        customerOrderQuery.setPageSize(rows);
        return customerOrderServcie.queryByParamsForDataGrid(customerOrderQuery);
    }
}
