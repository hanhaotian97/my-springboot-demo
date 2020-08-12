package com.hht.myspringbootdemo.work;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hht.myspringbootdemo.MyspringbootdemoApplication;
import com.hht.myspringbootdemo.dao.TempBankCodeMapper;
import com.hht.myspringbootdemo.dao.TempBankSubbranchMapper;
import com.hht.myspringbootdemo.entity.TempBankCodeDO;
import com.hht.myspringbootdemo.entity.TempBankSubbranchDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <br/>Author hanhaotian
 * <br/>Description :
 * <br/>CreateTime 2020/4/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyspringbootdemoApplication.class)
public class test_bankCode {
    @Autowired
    private TempBankCodeMapper tempBankCodeMapper;
    @Autowired
    private TempBankSubbranchMapper tempBankSubbranchMapper;

    @Test
    public void test() throws Exception {
        File file = new File("/Users/hanhaotian/Downloads/googleBrowserDown/省市区编号对照表.xlsx");
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        List<TempBankCodeDO> result = ExcelImportUtil.importExcel(file, TempBankCodeDO.class, params);

        List<TempBankCodeDO> tempBankCodeDOS = new ArrayList<>();
        for (TempBankCodeDO temp : result) {
            String[] split = temp.getRegionFullName().split(",");
            if (split.length > 4) {
                System.out.println(Arrays.toString(split));
            }
        }
    }

    @Test
    public void testImportInsertSubbranch() throws Exception {
        File file = new File("/Users/hanhaotian/Downloads/googleBrowserDown/《开户银行全称（含支行）对照表》-2020.03.10.xlsx");
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        List<TempBankSubbranchDO> result = ExcelImportUtil.importExcel(file, TempBankSubbranchDO.class, params);

        System.out.println(result.size());
        for (TempBankSubbranchDO tempBankSubbranchDO : result) {
            tempBankSubbranchDO.setEnabled(1);
            tempBankSubbranchDO.setAddTime(new Timestamp(System.currentTimeMillis()));
        }
        int i = tempBankSubbranchMapper.insertList(result);
        System.out.println(i);
    }

    @Test
    public void testImportInsert() throws Exception {
        //List<TempBankCodeDO> list = tempBankCodeMapper.list();
        //System.out.println(list);

        File file = new File("/Users/hanhaotian/Downloads/googleBrowserDown/省市区编号对照表.xlsx");

        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        List<TempBankCodeDO> result = ExcelImportUtil.importExcel(file, TempBankCodeDO.class, params);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Integer parentId_province = 0;  //省id
        Integer parentId_city = 0;  //市id
        for (TempBankCodeDO temp : result) {
            String[] split = temp.getRegionFullName().split(",");
            //直辖市
            if (split[1].equals("")) {
                split[1] = split[2].replace("市", "");

                if (split.length == 3) {
                    TempBankCodeDO municipalitiesBank = new TempBankCodeDO();
                    municipalitiesBank.setRegionCode(temp.getRegionCode());
                    municipalitiesBank.setRegionFullName(temp.getRegionFullName());
                    municipalitiesBank.setRegionName(split[1]);
                    municipalitiesBank.setRegionLevel(1);
                    municipalitiesBank.setParentId(0);
                    municipalitiesBank.setEnabled(1);
                    municipalitiesBank.setAddTime(timestamp);
                    int insetCount = tempBankCodeMapper.insert(municipalitiesBank);
                    if (insetCount <= 0) {
                        System.out.println("直辖市插入失败" + Arrays.toString(split));
                    }
                    parentId_province = municipalitiesBank.getId();
                }
            }

            TempBankCodeDO tempBankCodeDO = new TempBankCodeDO();
            tempBankCodeDO.setRegionCode(temp.getRegionCode());
            tempBankCodeDO.setRegionFullName(temp.getRegionFullName());
            tempBankCodeDO.setRegionName(split[split.length - 1]);
            if (split.length == 2) {
                tempBankCodeDO.setRegionLevel(1);
                tempBankCodeDO.setParentId(0);
            } else if (split.length == 3) {
                tempBankCodeDO.setRegionLevel(2);
                tempBankCodeDO.setParentId(parentId_province);
            } else if (split.length == 4) {
                tempBankCodeDO.setRegionLevel(3);
                tempBankCodeDO.setParentId(parentId_city);
            }
            tempBankCodeDO.setEnabled(1);
            tempBankCodeDO.setAddTime(timestamp);

            int insetCount = tempBankCodeMapper.insert(tempBankCodeDO);
            if (insetCount < 0) {
                System.out.println("插入失败" + Arrays.toString(split));
            }

            if (split.length == 2) {
                parentId_province = tempBankCodeDO.getId();
            }
            if (split.length == 3) {
                parentId_city = tempBankCodeDO.getId();
            }
        }

        //List<TempBankCodeDO> tempBankCodeDOS = new ArrayList<>();
        //int insetCount = tempBankCodeMapper.insertList(tempBankCodeDOS);
        //System.out.println(insetCount);
    }
}
