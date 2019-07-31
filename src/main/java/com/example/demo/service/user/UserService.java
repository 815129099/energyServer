package com.example.demo.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.service.IService;
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
public interface UserService extends IService<User> {
    /*
    Set<String> findPermissions(String geNumber);

    Set<String> findRoles(String geNumber);
   boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean addUser(User user);

    boolean getCode(String phone);

    boolean resetPassword(String phone);


    List<User> testUserList();

    JSONObject getUser(String username, String password);*/
    /**
     * 查询某用户的 角色  菜单列表   权限列表

    JSONObject getUserPermission(String username);*/
    Page<User> userList(String parameter, int pageNum, int pageSize);



    User getUserByNumber(String geNumber);

    /**
     * 用户列表
     */
    JSONObject listUser(JSONObject jsonObject);

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    JSONObject getAllRoles();

    /**
     * 添加用户
     */
    JSONObject addUser(JSONObject jsonObject);

    /**
     * 修改用户
     */
    JSONObject updateUser(JSONObject jsonObject);

    /**
     * 角色列表
     */
    JSONObject listRole();

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    JSONObject listAllPermission();

    /**
     * 添加角色
     */
    JSONObject addRole(JSONObject jsonObject);

    /**
     * 修改角色
     */
    JSONObject updateRole(JSONObject jsonObject);

    /**
     * 删除角色
     */
    JSONObject deleteRole(JSONObject jsonObject);
    /**
     * 通过MD5code获取用户
     */
    User getUserByCode(String code);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean addUser(User user);
}
