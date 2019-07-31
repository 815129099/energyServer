package com.example.demo.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lwx
 * @since 2019-04-25
 */
public class Record extends Model<Record> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    private String sessionId;

    /**
     * IP
     */
    private String IP;

    /**
     * 创建时间
     */
    private String CreatedDateTime;

    /**
     * 创建时间
     */
    private String UpdatedDateTime;

    private Integer Status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
    public String getCreatedDateTime() {
        return CreatedDateTime;
    }

    public void setCreatedDateTime(String CreatedDateTime) {
        this.CreatedDateTime = CreatedDateTime;
    }
    public String getUpdatedDateTime() {
        return UpdatedDateTime;
    }

    public void setUpdatedDateTime(String UpdatedDateTime) {
        this.UpdatedDateTime = UpdatedDateTime;
    }
    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Record{" +
        "id=" + id +
        ", userName=" + userName +
        ", sessionId=" + sessionId +
        ", IP=" + IP +
        ", CreatedDateTime=" + CreatedDateTime +
        ", UpdatedDateTime=" + UpdatedDateTime +
        ", Status=" + Status +
        "}";
    }
}
