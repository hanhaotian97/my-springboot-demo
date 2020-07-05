package com.hht.myspringbootdemo.dao;

import com.hht.myspringbootdemo.entity.TempBankSubbranchDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 开户银行支行表(SystemBankSubbranch)表数据库访问层
 * @author hanhaotian
 * @since 2020-04-30 09:43:14
 */
@Repository
public interface TempBankSubbranchMapper {

    /**
     * 通过ID查询单条数据
     * @return 实例对象
     */
    int insertList(List<TempBankSubbranchDO> tempBankSubbranchDOS);
}