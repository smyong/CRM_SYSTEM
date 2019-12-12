package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    //根据用户Id查询出所对应的所有角色信息
    public int countUserRoleByUserId(Integer userId);
    //根据用户Id删除出所对应的所有角色信息
    public int deleteUserRoleByUserId(Integer userId);
    //根据角色Id查询出所有的角色信息
    public int countUserRoleByRoleId(Integer roleId);
    //根据角色Id删除出所有的角色信息
    public int deleteUserRoleByRoleId(Integer roleId);

}