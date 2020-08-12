package com.hht.myspringbootdemo.work;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.hht.myspringbootdemo.dao.TempSljzMapper;
import com.hht.myspringbootdemo.entity.TempSljzUserDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2019-08-15
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MyspringbootdemoApplication.class)
public class test_sljz {

    //@Autowired
    private TempSljzMapper tempSljzMapper;

    //@Test
    public void test() throws Exception {
        List<TempSljzUserDTO> exportUserList = new ArrayList<>();

        //List<String> accountList = tempSljzMapper.listAccountAll();
        //List<String> accountList = tempSljzMapper.listAccountById(userIdList);
        List<String> accountList = new ArrayList<>();
        accountList.add("sytjj770");
        accountList.add("18930142687");
        accountList.add("syf9888");

        HashSet<String> distinctAccountList = new HashSet<>(accountList);
        String account;
        for (String s : accountList) {
            account = s;

            TempSljzUserDTO user = tempSljzMapper.getAccount(account);
            user.setLevel("本人");
            exportUserList.add(user);

            TempSljzUserDTO userUp = tempSljzMapper.getAccountUp(account);
            userUp.setLevel("上级");
            exportUserList.add(userUp);

            List<TempSljzUserDTO> userDownOne = tempSljzMapper.getAccountDownOne(account);
            for (TempSljzUserDTO tempDTO : userDownOne) {
                tempDTO.setLevel("下一级");
                exportUserList.add(tempDTO);
            }

            List<TempSljzUserDTO> userDownTwo = tempSljzMapper.getAccountDownTwo(account);
            for (TempSljzUserDTO tempDTO : userDownTwo) {
                tempDTO.setLevel("下二级");
                exportUserList.add(tempDTO);
            }

            List<TempSljzUserDTO> userDownThree = tempSljzMapper.getAccountDownThree(account);
            for (TempSljzUserDTO tempDTO : userDownThree) {
                tempDTO.setLevel("下三级");
                exportUserList.add(tempDTO);
            }

            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("会员上下级关系列表","数据"),
                    TempSljzUserDTO.class, exportUserList);
            File saveFile = new File("E:/excel/sljz上下级账户@20191129");
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }

            StringBuilder fileName = new StringBuilder("sljz上下级账户@20191129/");
            //if (user.getIdCardNo().substring(0, 2).equals("33")) {
            //    fileName.append("33/");
            //} else {
            //    fileName.append("32/");
            //}

            fileName.append(user.getIdCardNo());
            if (!distinctAccountList.add(user.getIdCardNo())) {
                fileName.append("-").append(user.getUserAccount());
            }
            FileOutputStream fos = new FileOutputStream("E:/excel/"+ fileName +".xls");
            workbook.write(fos);
            fos.close();
        }
    }

    private List<Integer> userIdList = new ArrayList<Integer>(){
        {
            add(31810);
            add(21893);
        }
    };
}
