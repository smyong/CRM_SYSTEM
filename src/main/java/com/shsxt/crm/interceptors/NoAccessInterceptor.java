package com.shsxt.crm.interceptors;

import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 非法访问拦截
 */
public class NoAccessInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取Cookie中的userID 判断是否存在 若存在放行 否则拦截
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        if(null==userId||null==userService.queryById(userId)){
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }
        return true;
    }
}
