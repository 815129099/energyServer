package com.example.demo.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.example.demo.util.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
@Excel("用户列表")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @ExcelField(value = "ID",width = 30)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private int roleId;

    @ExcelField(value = "工号",required = true)
    private String geNumber;

    @ExcelField(value = "用户名")
    private String geName;

    @ExcelField(value = "密码")
    private String password;

    @ExcelField(value = "用户状态")
    private String userState;

    @ExcelField(value = "手机号")
    private String phone;

    @ExcelField(value = "邮箱")
    private String email;

    @ExcelField(value = "创建时间",writeConverter = TimeConverter.class)
    private String createTime;

    @ExcelField(value = "修改时间",writeConverter = TimeConverter.class)
    private String updateTime;

    @ExcelField(value = "验证码")
    private String code;

    private String md5Code;

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getGeNumber() {
        return geNumber;
    }

    public void setGeNumber(String geNumber) {
        this.geNumber = geNumber;
    }
    public String getGeName() {
        return geName;
    }

    public void setGeName(String geName) {
        this.geName = geName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }
    public String getPhone() {
        return phone;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", geNumber=" + geNumber +
        ", geName=" + geName +
        ", password=" + password +
        ", userState=" + userState +
        ", phone=" + phone +
        ", roleId=" + roleId +
        ", email=" + email +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
