package com.shsxt.crm.proxy;

import com.shsxt.crm.annotations.RequirePermission;
import com.shsxt.crm.utils.AssertUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {

    @Autowired
    private HttpSession session;

    @Pointcut("@annotation(com.shsxt.crm.annotations.RequirePermission)")
    public void cut(){}

    @Around(value = "cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result=null;
        List<String> aclValues= (List<String>) session.getAttribute("permissions");
        System.out.println(aclValues);
        MethodSignature methodSignature= (MethodSignature) pjp.getSignature();

        RequirePermission requirePermission=methodSignature.getMethod().getAnnotation(RequirePermission.class);
        String aclValue=requirePermission.aclValue();
        AssertUtil.isTrue(!(aclValues.contains(aclValue)),"权限不足");
        result=pjp.proceed();
        return result;
    }
}

