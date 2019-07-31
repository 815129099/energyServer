package com.example.demo.service.user;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Record;
import com.example.demo.entity.User;
import com.example.demo.mapper.user.LoginDao;
import com.example.demo.service.util.RecordService;
import com.example.demo.util.CommonUtil;
import com.example.demo.util.shiro.Constants;
import com.example.demo.util.shiro.NetworkUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserService userService;

    /**
     * 登录表单提交
     */
    @Override
    public JSONObject authLogin(JSONObject jsonObject, HttpServletRequest request) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        if(StringUtils.isEmpty(password)){
            User user = userService.getUserByCode(username);
            if(StringUtils.isEmpty(user)){
                info.put("result","用户不存在");
                return CommonUtil.successJson(info);
            }else {
                username = user.getGeNumber();
                password = user.getPassword();
            }
        }
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            info.put("result", "success");
            Session session = SecurityUtils.getSubject().getSession();
            System.out.println(session.getHost());
            System.out.println(request.getHeaderNames().toString());

            Serializable sessionId = session.getId();
            Record record = new Record();
            record.setSessionId(sessionId.toString());
            record.setIP(NetworkUtil.getIpAddr(request));
            record.setUserName(username);
            recordService.insert(record);
/**
 * 认证异常
 * <p>
 * org.apache.shiro.authc.pam.UnsupportedTokenException 身份令牌异常，不支持的身份令牌
 * org.apache.shiro.authc.UnknownAccountException       未知账户/没找到帐号,登录失败
 * org.apache.shiro.authc.LockedAccountException        帐号锁定
 * org.apache.shiro.authz.DisabledAccountException      用户禁用
 * org.apache.shiro.authc.ExcessiveAttemptsException    登录重试次数，超限。只允许在一段时间内允许有一定数量的认证尝试
 * org.apache.shiro.authc.ConcurrentAccessException     一个用户多次登录异常：不允许多次登录，只能登录一次 。即不允许多处登录
 * org.apache.shiro.authz.AccountException              账户异常
 * org.apache.shiro.authz.ExpiredCredentialsException   过期的凭据异常
 * org.apache.shiro.authc.IncorrectCredentialsException 错误的凭据异常
 * org.apache.shiro.authc.CredentialsException          凭据异常
 * org.apache.shiro.authc.AuthenticationException       上面异常的父类
 *
 * @param ex 没有权限的异常
 * @return ModelAndView
 */

        }  catch (DisabledAccountException e) {
            info.put("result", "账号未启用");
        } catch (IncorrectCredentialsException e) {
            info.put("result", "错误的凭据");
        } catch (UnknownAccountException e) {
            info.put("result", "未知账户/没找到帐号,登录失败");
        }catch (AccountException e) {
            info.put("result", "账号异常");
        }catch (AuthenticationException e){
            info.put("result", "用户名、密码错误");
        }
        return CommonUtil.successJson(info);
    }

    /**
     * 根据用户名和密码查询对应的用户
     */
    @Override
    public JSONObject getUser(String username, String password) {
        return loginDao.getUser(username, password);
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getString("username");
        JSONObject info = new JSONObject();
        JSONObject userPermission = permissionService.getUserPermission(username);
        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
        info.put("userPermission", userPermission);
        return CommonUtil.successJson(info);
    }

    /**
     * 退出登录
     */
    @Override
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return CommonUtil.successJson();
    }
}
