package com.shsxt.crm.controller;
/**
 * 用户登录模块控制层
 */

import com.shsxt.base.BaseController;
import com.shsxt.crm.annotations.RequirePermission;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@SuppressWarnings("all")
public class UserContoller extends BaseController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param userName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName, String password, HttpSession session){
        ResultInfo resultInfo=new ResultInfo();
        UserModel userModel=userService.login(userName,password);
        resultInfo.setResult(userModel);
        return resultInfo;
    }

    /**
     * 修改用户密码
     * @param request 请求域对象
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param confirmPassword 确认新密码
     * @return 回显信息对象
     */
    @RequestMapping("user/updateUserPassword")
    @ResponseBody
    @RequirePermission(aclValue = "6040")
    public ResultInfo updateUserPassword(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
        ResultInfo resultInfo=new ResultInfo();
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request),oldPassword,newPassword,confirmPassword);
        resultInfo.setMsg("密码修改成功！");
        return resultInfo;
    }

    /**
     * 进入用户管理界面
     * @return
     */
    @RequestMapping("user/index")
    public String index(){
        return "user";
    }

    /**
     *查询出用户信息
     * @param userQuery 需要返回前台的用户信息对象
     * @param page 起始页
     * @param rows 一页几条数据
     * @return
     */
    @RequestMapping("user/list")
    @ResponseBody
    public Map<String,Object> queryUsersByParams(UserQuery userQuery, @RequestParam(defaultValue = "1")Integer page,
                                                 @RequestParam(defaultValue = "10")Integer rows){
            //设置分页的第几页和一页几条数据  ---->默认第一页共十条
            userQuery.setPageNum(page);
            userQuery.setPageSize(rows);
            //返回查询的数据
        return userService.queryByParamsForDataGrid(userQuery);
    }

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    @RequestMapping("user/save")
    @ResponseBody
    public ResultInfo save(User user){
        userService.saveUser(user);
        return success("用户数据添加成功！");
    }
    @RequestMapping("user/delete")
    @ResponseBody
    public ResultInfo delete(@RequestParam(name = "id") Integer userId){
        userService.deleteUser(userId);
        return success("用户数据删除成功");
    }
    @RequestMapping("user/update")
    @ResponseBody
    public ResultInfo update(User user){
        userService.updateUser(user);
        return success("用户信息更新成功");
    }


}
