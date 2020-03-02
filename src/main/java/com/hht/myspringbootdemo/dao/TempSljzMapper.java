package com.hht.myspringbootdemo.dao;

import com.hht.myspringbootdemo.entity.TempSljzUserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2019-11-16
 */
@Repository
public interface TempSljzMapper {
    List<String> listAccountAll();
    List<String> listAccountById(List<Integer> idList);

    TempSljzUserDTO getAccount(String account);
    TempSljzUserDTO getAccountUp(String account);
    List<TempSljzUserDTO> getAccountDownOne(String account);
    List<TempSljzUserDTO> getAccountDownTwo(String account);
    List<TempSljzUserDTO> getAccountDownThree(String account);

}
