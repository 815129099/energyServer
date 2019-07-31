package com.example.demo.service.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo.entity.User;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
public interface PermissionService  {

    /**
     * 查询某用户的 角色  菜单列表   权限列表
     */
    JSONObject getUserPermission(String username);

}
