package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.service.PermissionService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.Permission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.jnlp.PersistenceService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    /**
     * 进入首页登录界面
     * @return 请求路径
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("index02")
    public String index02(){
        return "index";
    }

    /**
     * 判断用户登录状态，如果登录进入主页面
     * 并在request请求域存入user对象
     * @param request
     * @return 请求路径
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("user",userService.queryById(userId));

        List<String> permissions =permissionService.queryAllModuleAclValueByUserId(userId);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }
}
