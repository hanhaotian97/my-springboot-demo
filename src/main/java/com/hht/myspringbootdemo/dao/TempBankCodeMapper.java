package com.hht.myspringbootdemo.dao;


import com.hht.myspringbootdemo.entity.TempBankCodeDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2020/4/26
 */

@Repository
public interface TempBankCodeMapper {

    List<TempBankCodeDO> list();

    int insertList(List<TempBankCodeDO> tempBankCodeDOS);

    int insert(TempBankCodeDO tempBankCodeDO);
}
