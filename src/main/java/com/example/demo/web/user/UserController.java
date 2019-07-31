package com.example.demo.web.user;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Params;
import com.example.demo.entity.User;
import com.example.demo.service.user.UserService;
import com.example.demo.util.CommonUtil;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx
 * @since 2019-03-21

@CrossOrigin
@Controller
public class UserController {

    @Autowired
    private ExceptionHandle exceptionHandle;

    @Autowired
    private UserService userService;






}
*/
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/UserList.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getUserList(@RequestBody Params param) {
        logger.info(param.getParameter()+","+param.getPageNum()+","+param.getPageSize());
        HashMap<String, Object> studentMap = new HashMap<String, Object>();
        Page<User> page = userService.userList(param.getParameter(), Integer.parseInt(param.getPageNum()), Integer.parseInt(param.getPageSize()));
        studentMap.put("userData", page);
        studentMap.put("number", page.getTotal());
        Result result = ResultUtil.success();
        result.setData(studentMap);
        return result;
    }
    /**
     * 查询用户列表
     */
    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public JSONObject listUser(HttpServletRequest request) {
        return userService.listUser(CommonUtil.request2Json(request));
    }
    /*
        @RequiresPermissions("user:add")
        @PostMapping("/addUser")
        public JSONObject addUser(@RequestBody JSONObject requestJson) {
            CommonUtil.hasAllRequired(requestJson, "username, password, nickname,roleId,phone,email");
            return userService.addUser(requestJson);
        }

        @RequiresPermissions("user:update")
        @PostMapping("/updateUser")
        public JSONObject updateUser(@RequestBody JSONObject requestJson) {
            CommonUtil.hasAllRequired(requestJson, " nickname,   roleId, deleteStatus, userId");
            return userService.updateUser(requestJson);
        }
    */
    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllRoles")
    public JSONObject getAllRoles() {
        return userService.getAllRoles();
    }

    /**
     * 角色列表
     */
    @RequiresPermissions("role:list")
    @GetMapping("/listRole")
    public JSONObject listRole() {
        return userService.listRole();
    }

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    @RequiresPermissions("role:list")
    @GetMapping("/listAllPermission")
    public JSONObject listAllPermission() {
        return userService.listAllPermission();
    }

    /**
     * 新增角色
     */
    @RequiresPermissions("role:add")
    @PostMapping("/addRole")
    public JSONObject addRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
        return userService.addRole(requestJson);
    }

    /**
     * 修改角色
     */
    @RequiresPermissions("role:update")
    @PostMapping("/updateRole")
    public JSONObject updateRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");
        return userService.updateRole(requestJson);
    }

    /**
     * 删除角色
     */
    @RequiresPermissions("role:delete")
    @PostMapping("/deleteRole")
    public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId");
        return userService.deleteRole(requestJson);
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/User.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.PUT})
    @ResponseBody
    public Result updateUser(@RequestBody User user) {
        logger.info(user.getGeNumber()+","+user.getUserState());
        boolean isSuccess =false;
        Result result = ResultUtil.success();
        try{
            isSuccess= userService.updateUser(user);
        }catch (Exception e){
            result.setData("修改失败");
            return result;
        }
        result.setData(isSuccess);
        return result;
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/User.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.DELETE})
    @ResponseBody
    public Result deleteUser(@RequestBody User user) {
        logger.info(user.getGeNumber());
        boolean isSuccess =false;
        Result result = ResultUtil.success();
        try{
            isSuccess= userService.deleteUser(user);
        }catch (Exception e){
            result.setData("删除失败");
            return result;
        }
        result.setData(isSuccess);
        return result;
    }

    @RequiresPermissions("user:add")
    @RequestMapping(value = "/User.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result addUser(@RequestBody User user) {
        logger.info(user.toString());
        boolean isSuccess = false;
        Result result = ResultUtil.success();
        try{
            isSuccess = userService.addUser(user);
        }catch (DuplicateKeyException e){
            result.setData("数据冲突！");
            return result;
        }catch (Exception exception){
            result.setData("添加失败！");
            return result;
        }
        result.setData(isSuccess);
        return result;
    }
}