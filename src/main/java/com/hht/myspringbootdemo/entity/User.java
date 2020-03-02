package com.hht.myspringbootdemo.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 测试用表(User)实体类
 *
 * @author hanhaotian
 * @since 2019-11-08 14:50:58
 */
public class User implements Serializable {
    private static final long serialVersionUID = -70904242322469044L;
    /**
    * 主键ID
    */
    private Integer id;
    /**
    * 用户名称
    */
    private String name;
    /**
    * 状态：1：正常，2：被删除
    */
    private Integer status;
    /**
    * 添加时间
    */
    private Date addtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

}