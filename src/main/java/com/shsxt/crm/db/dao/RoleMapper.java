package com.shsxt.crm.db.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

   //查询出该用户所有的角色
   public List<Map<String, Object>> queryAllRoles();
   //通过角色名称查询出对应的角色信息
   public Role queryRoleByRoleName(@Param("roleName") String roleName);
}