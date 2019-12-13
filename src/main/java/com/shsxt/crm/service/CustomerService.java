package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.db.dao.CustomerLossMapper;
import com.shsxt.crm.db.dao.CustomerMapper;
import com.shsxt.crm.db.dao.CustomerOrderMapper;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.Customer;
import com.shsxt.crm.vo.CustomerLoss;
import com.shsxt.crm.vo.CustomerOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService extends BaseService<Customer,Integer> {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerOrderMapper customerOrderMapper;
    @Autowired
    private CustomerLossMapper customerLossMapper;

    /**
     * 添加客户信息
     * @param customer
     */
    public void saveCustomer(Customer customer){
       //参数校验
        checkParams(customer);
        //验证客户的唯一性
        Customer temp=customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null != temp,"客户已存在！");
        //客户编号唯一性校验
        String khno="KH"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        customer.setKhno(khno);
        customer.setState(0);
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(save(customer)<1,"客户信息添加失败！");
    }

    private void checkParams(Customer customer) {
        AssertUtil.isTrue(StringUtils.isBlank(customer.getName()),"请输入客户名!");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getPhone()), "请输入手机号!");
        AssertUtil.isTrue(customer.getPhone().length() != 11, "手机号格式非法!");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getFr()), "请指定公司法人代表!");
    }

    /**
     * 更新客户信息
     * @param customer
     */
    public void updateCustomer(Customer customer){
        checkParams(customer);
        Customer temp=customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(customer.getId())),"客户已存在！");
        AssertUtil.isTrue(null == customer.getId()|| null== queryById(customer.getId()),"该客户不存在！");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(update(customer)<1,"客户更新失败");
    }

    /**
     * 删除客户信息
     * @param id
     */
    public void deleteCustomer(Integer id){
        Customer customer=queryById(id);
        AssertUtil.isTrue(customer==null||customer.getId()==null,"抱歉！待删除记录不存在！");
        customer.setIsValid(0);
        AssertUtil.isTrue(update(customer)<1,"客户删除失败！");
    }
    /**
     * 流失客户转移
     */
    public void updateCustomerState() {
        //从客户表获取到已满足流失客户的信息
        List<Customer> lossCustomers=customerMapper.queryLossCustomers();
        //初始化一个数组和以流失客户信息表的实体类集合对象
        Integer[]ids=null;
        List<CustomerLoss> customerLosses=null;
        //先判断从客户表查询的流失客户信息是否存在
        if(!(CollectionUtils.isEmpty(lossCustomers))){
            //把获取到的已流失的客户信息集合对象的长度赋与ids
            ids=new Integer[lossCustomers.size()];
            //把流失客户信息对象赋予流失客户实体类集合
            customerLosses=new ArrayList<CustomerLoss>();
            //循环遍历获取到的流失客户信息赋予到流失客户表实体类中
            for (int i=0;i<lossCustomers.size();i++){
                //把信息赋予客户表实体类对象便于利用其属性
                Customer customer=lossCustomers.get(i);
                //把客户ID赋予ids数组
                ids[i]=customer.getId();
                //构造客户流失表的实体类对象
                CustomerLoss customerLoss=new CustomerLoss();
                //设置其更新时间
                customerLoss.setUpdateDate(new Date());
                //设置其状态
                customerLoss.setState(0);
                //通过customer的id字段从订单表获取到该客户的最后一次订单记录
                CustomerOrder customerOrder=customerOrderMapper.queryLastCustomerOrderByCusId(customer.getId());
                //判断该记录是否存在
                if(null !=customerOrder){
                    //设置最后一次下单时间
                    customerLoss.setLastOrderTime(customerOrder.getOrderDate());
                }
                //设置流失客户表的其他属性
                customerLoss.setIsValid(1);
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setCreateDate(new Date());
                customerLosses.add(customerLoss);
            }
        }
    }

    /**
     * 通过客户编号获取客户信息
     * @param cusNo
     * @return
     */
    public Customer queryCustomerByCusNo(String cusNo) {
        return customerMapper.queryCustomerByCusNo(cusNo);
    }
}
