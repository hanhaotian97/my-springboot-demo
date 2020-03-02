package com.hht.myspringbootdemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2019-11-16
 */
@Getter
@Setter
public class TempSljzUserDTO {
    @Excel(name = "层级", orderNum = "1", width = 25,needMerge = true)
    private String level;
    @Excel(name = "登录账号", orderNum = "1", width = 25,needMerge = true)
    private String userAccount;
    @Excel(name = "手机号", orderNum = "1", width = 25,needMerge = true)
    private String boundPhoneNumber;
    @Excel(name = "姓名", orderNum = "1", width = 25,needMerge = true)
    private String idCardName;
    @Excel(name = "身份证", orderNum = "1", width = 25,needMerge = true)
    private String idCardNo;
    @Excel(name = "上级账号", orderNum = "1", width = 25,needMerge = true)
    private String upOne;
}
