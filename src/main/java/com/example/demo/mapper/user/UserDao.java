package com.example.demo.mapper.user;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Record;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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

public interface UserDao extends BaseMapper<User> { */
@Mapper
public interface UserDao extends BaseMapper<User>{
    /*
    Set<String> findRoles(String geNumber);

    Set<String> findPermissions(String geNumber);

    List<User> testUserList();

    JSONObject getUser(@Param("username") String username, @Param("password") String password);

    /**
     * 查询用户的角色 菜单 权限

    JSONObject getUserPermission(String username);
*/
    /**
     * 查询所有的菜单

    Set<String> getAllMenu();
     */
    /**
     * 查询所有的权限

    Set<String> getAllPermission();
     */
    User getUserByNumber(String geNumber);
    List<User> userList(@Param("parameter") String parameter);
    /**
     * 查询用户数量
     */
    int countUser(JSONObject jsonObject);

    /**
     * 查询用户列表
     */
    List<JSONObject> listUser(JSONObject jsonObject);

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    List<JSONObject> getAllRoles();

    /**
     * 校验用户名是否已存在
     */
    int queryExistUsername(JSONObject jsonObject);

    /**
     * 新增用户
     */
    int addUser(JSONObject jsonObject);

    /**
     * 修改用户
     */
    int updateUser(JSONObject jsonObject);

    /**
     * 角色列表
     */
    List<JSONObject> listRole();

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    List<JSONObject> listAllPermission();

    /**
     * 新增角色
     */
    int insertRole(JSONObject jsonObject);

    /**
     * 批量插入角色的权限
     *
     * @param roleId      角色ID
     * @param permissions 权限
     */
    int insertRolePermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

    /**
     * 将角色曾经拥有而修改为不再拥有的权限 delete_status改为'2'
     */
    int removeOldPermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

    /**
     * 修改角色名称
     */
    int updateRoleName(JSONObject jsonObject);

    /**
     * 查询某角色的全部数据
     * 在删除和修改角色时调用
     */
    JSONObject getRoleAllInfo(JSONObject jsonObject);

    /**
     * 删除角色
     */
    int removeRole(JSONObject jsonObject);

    /**
     * 删除本角色全部权限
     */
    int removeRoleAllPermission(JSONObject jsonObject);
    /**
     * 通过MD5code获取用户
     */
    User getUserByCode(@Param("md5Code") String code);

}
