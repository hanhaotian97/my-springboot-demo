package com.hht.myspringbootdemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2020/4/26
 */
@Getter
@Setter
@ToString
public class TempBankCodeDO {
    /**
     * 记录id
     */
    private Integer id;

    /**
     * 上一级Id，没有上一级填0
     */
    private Integer parentId;

    /**
     * 级别，1：省，2：市，3：区县
     */
    private Integer regionLevel;

    /**
     * 省市编码
     */
    @Excel(name = "省市编码")
    private String regionCode;

    /**
     * 地区名称
     */
    private String regionName;

    /**
     * 地区全名称
     */
    @Excel(name = "地区名称")
    private String regionFullName;

    /**
     * 是否启用
     */
    private Integer enabled;

    /**
     * 创建时间
     */
    private Timestamp addTime;
}
