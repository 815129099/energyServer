package com.example.demo.mapper.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
@Mapper
public interface PermissionDao {
    /**
     * 查询用户的角色 菜单 权限
     */
    JSONObject getUserPermission(String username);

    /**
     * 查询所有的菜单
     */
    Set<String> getAllMenu();

    /**
     * 查询所有的权限
     */
    Set<String> getAllPermission();



}
