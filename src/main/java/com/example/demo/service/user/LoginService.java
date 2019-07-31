package com.example.demo.service.user;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
public interface LoginService {
    /**
     * 登录表单提交
     */
    JSONObject authLogin(JSONObject jsonObject, HttpServletRequest request);

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     */
    JSONObject getUser(String username, String password);

    /**
     * 查询当前登录用户的权限等信息
     */
    JSONObject getInfo();

    /**
     * 退出登录
     */
    JSONObject logout();

}
