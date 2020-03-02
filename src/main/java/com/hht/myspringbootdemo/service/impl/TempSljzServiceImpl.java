package com.hht.myspringbootdemo.service.impl;

import com.hht.myspringbootdemo.dao.TempSljzMapper;
import com.hht.myspringbootdemo.entity.TempSljzUserDTO;
import com.hht.myspringbootdemo.service.ITempSljzService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2019-11-16
 */
@Service
public class TempSljzServiceImpl implements ITempSljzService {
    @Resource
    private TempSljzMapper tempSljzMapper;

    @Override
    public List<String> listAccountAll() {
        return tempSljzMapper.listAccountAll();
    }

    @Override
    public TempSljzUserDTO getAccount(String account) {
        return null;
    }

    @Override
    public TempSljzUserDTO getAccountUp(String account) {
        return null;
    }

    @Override
    public List<TempSljzUserDTO> getAccountDownOne(String account) {
        return null;
    }

    @Override
    public List<TempSljzUserDTO> getAccountDownTwo(String account) {
        return null;
    }

    @Override
    public List<TempSljzUserDTO> getAccountDownThree(String account) {
        return null;
    }
}
