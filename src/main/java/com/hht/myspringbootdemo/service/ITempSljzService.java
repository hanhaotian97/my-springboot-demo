package com.hht.myspringbootdemo.service;

import com.hht.myspringbootdemo.entity.TempSljzUserDTO;

import java.util.List;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2019-11-16
 */
public interface ITempSljzService {

    List<String> listAccountAll();

    TempSljzUserDTO getAccount(String account);
    TempSljzUserDTO getAccountUp(String account);
    List<TempSljzUserDTO> getAccountDownOne(String account);
    List<TempSljzUserDTO> getAccountDownTwo(String account);
    List<TempSljzUserDTO> getAccountDownThree(String account);
}
