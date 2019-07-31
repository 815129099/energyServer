package com.example.demo.service.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.user.UserDao;
import com.example.demo.util.CommonUtil;
import com.example.demo.util.md5.MD5Utils;
import com.example.demo.util.shiro.ErrorEnum;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
/*
    @Autowired
    private UserDao userDao;

    @Override
    public Set<String> findPermissions(String geNumber) {
        return this.userDao.findPermissions(geNumber);
    }

    @Override
    public Set<String> findRoles(String geNumber) {
        return this.userDao.findRoles(geNumber);
    }

     @Override
    @Transactional
    public boolean updateUser(User user) {
        boolean isSuccess = false;
        userDao.update(user,new EntityWrapper<User>().eq("geNumber",user.getGeNumber()));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean deleteUser(User user) {
        boolean isSuccess = false;
        user.setUserState("删除");
        userDao.update(user,new EntityWrapper<User>().eq("geNumber",user.getGeNumber()));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        boolean isSuccess = false;
        userDao.insert(user);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean getCode(String phone) {
        boolean isSuccess = false;
        User user = new User();
        String code = SendMessageUtil.getRandomCode(6);
        user.setCode(code);
        //SendMessageUtil.send(phone,code);
        userDao.update(user,new EntityWrapper<User>().eq("phone",phone));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean resetPassword(String phone) {
        boolean isSuccess = false;
        User user = new User();
        String password = SendMessageUtil.getRandomCode(8);
        user.setPassword(password);
        //SendMessageUtil.send(phone,code);
        userDao.update(user,new EntityWrapper<User>().eq("phone",phone));
        isSuccess = true;
        return isSuccess;
    }



    @Override
    public List<User> testUserList() {
        return this.userDao.testUserList();
    }

    @Override
    public JSONObject getUser(String username, String password) {
        return userDao.getUser(username, password);
    }
*/
    /**
     * 查询某用户的 角色  菜单列表   权限列表

    @Override
    public JSONObject getUserPermission(String username) {
        JSONObject userPermission = getUserPermissionFromDB(username);
        return userPermission;
    }*/

    /**
     * 从数据库查询用户权限信息

    private JSONObject getUserPermissionFromDB(String username) {
        JSONObject userPermission = userDao.getUserPermission(username);
        //管理员角色ID为1
        int adminRoleId = 1;
        //如果是管理员
        String roleIdKey = "roleId";
        if (adminRoleId == userPermission.getIntValue(roleIdKey)) {
            //查询所有菜单  所有权限
            Set<String> menuList = userDao.getAllMenu();
            Set<String> permissionList = userDao.getAllPermission();
            userPermission.put("menuList", menuList);
            userPermission.put("permissionList", permissionList);
        }
        return userPermission;
    }
     */















    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByNumber(String geNumber) {
        return this.userDao.getUserByNumber(geNumber);
    }

    @Override
    public Page<User> userList(String parameter, int pageNum, int pageSize) {
        Page<User> page = PageHelper.startPage(pageNum, pageSize);
        userDao.userList(parameter);
        return page;
    }


    /**
     * 用户列表
     */
    @Override
    public JSONObject listUser(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = userDao.countUser(jsonObject);
        List<JSONObject> list = userDao.listUser(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 添加用户
     */
    @Override
    public JSONObject addUser(JSONObject jsonObject) {
        int exist = userDao.queryExistUsername(jsonObject);
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        String username = jsonObject.getString("username");
        jsonObject.put("md5Code", MD5Utils.string2MD5(username+"716"));
        userDao.addUser(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    @Override
    public JSONObject getAllRoles() {
        List<JSONObject> roles = userDao.getAllRoles();
        return CommonUtil.successPage(roles);
    }

    /**
     * 修改用户
     */
    @Override
    public JSONObject updateUser(JSONObject jsonObject) {
        userDao.updateUser(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 角色列表
     */
    @Override
    public JSONObject listRole() {
        List<JSONObject> roles = userDao.listRole();
        return CommonUtil.successPage(roles);
    }

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    @Override
    public JSONObject listAllPermission() {
        List<JSONObject> permissions = userDao.listAllPermission();
        return CommonUtil.successPage(permissions);
    }

    /**
     * 添加角色
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject addRole(JSONObject jsonObject) {
        userDao.insertRole(jsonObject);
        userDao.insertRolePermission(jsonObject.getString("roleId"), (List<Integer>) jsonObject.get("permissions"));
        return CommonUtil.successJson();
    }

    /**
     * 修改角色
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject updateRole(JSONObject jsonObject) {
        String roleId = jsonObject.getString("roleId");
        List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
        JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
        Set<Integer> oldPerms = (Set<Integer>) roleInfo.get("permissionIds");
        //修改角色名称
        dealRoleName(jsonObject, roleInfo);
        //添加新权限
        saveNewPermission(roleId, newPerms, oldPerms);
        //移除旧的不再拥有的权限
        removeOldPermission(roleId, newPerms, oldPerms);
        return CommonUtil.successJson();
    }

    /**
     * 修改角色名称
     */
    private void dealRoleName(JSONObject paramJson, JSONObject roleInfo) {
        String roleName = paramJson.getString("roleName");
        if (!roleName.equals(roleInfo.getString("roleName"))) {
            userDao.updateRoleName(paramJson);
        }
    }

    /**
     * 为角色添加新权限
     */
    private void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitInsert = new ArrayList<>();
        for (Integer newPerm : newPerms) {
            if (!oldPerms.contains(newPerm)) {
                waitInsert.add(newPerm);
            }
        }
        if (waitInsert.size() > 0) {
            userDao.insertRolePermission(roleId, waitInsert);
        }
    }

    /**
     * 删除角色 旧的 不再拥有的权限
     */
    private void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitRemove = new ArrayList<>();
        for (Integer oldPerm : oldPerms) {
            if (!newPerms.contains(oldPerm)) {
                waitRemove.add(oldPerm);
            }
        }
        if (waitRemove.size() > 0) {
            userDao.removeOldPermission(roleId, waitRemove);
        }
    }

    /**
     * 删除角色
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject deleteRole(JSONObject jsonObject) {
        JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
        List<JSONObject> users = (List<JSONObject>) roleInfo.get("users");
        if (users != null && users.size() > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10008);
        }
        userDao.removeRole(jsonObject);
        userDao.removeRoleAllPermission(jsonObject);
        return CommonUtil.successJson();
    }
    /**
     * 通过MD5code获取用户
     */
    @Override
    public User getUserByCode(String code) {
        return this.userDao.getUserByCode(code);
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        boolean isSuccess = false;
        userDao.update(user,new EntityWrapper<User>().eq("geNumber",user.getGeNumber()));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean deleteUser(User user) {
        boolean isSuccess = false;
        user.setUserState("删除");
        userDao.update(user,new EntityWrapper<User>().eq("geNumber",user.getGeNumber()));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        boolean isSuccess = false;
        user.setMd5Code(MD5Utils.string2MD5(user.getGeNumber().toUpperCase()+"716"));
        userDao.insert(user);
        isSuccess = true;
        return isSuccess;
    }

}
