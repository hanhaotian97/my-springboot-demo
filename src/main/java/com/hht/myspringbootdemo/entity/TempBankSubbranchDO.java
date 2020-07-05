package com.hht.myspringbootdemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2020/4/30
 */
@Getter
@Setter
public class TempBankSubbranchDO {
    /**
     * 记录id
     */
    private Integer id;

    /**
     * 开户银行全称
     */
    @Excel(name = "支行名称")
    private String bankName;

    /**
     * 联行号
     */
    @Excel(name = "联行号")
    private String bankBranchId;

    /**
     * 是否启用，1：启用，2：不启用
     */
    private Integer enabled;

    /**
     * 添加时间
     */
    private Timestamp addTime;
}
