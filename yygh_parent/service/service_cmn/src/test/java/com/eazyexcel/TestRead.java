package com.eazyexcel;/*
 *@author 周欢
 *@version 1.0
 */

import com.alibaba.excel.EasyExcel;
import com.yygh.model.acl.User;

public class TestRead {
    public static void main(String[] args) {
        //设置excel文件名称和路径
        String fileName="E:\\excel\\01.xlsx";

        EasyExcel.read(fileName, UserData.class,new ExcelListener()).sheet().doRead();
    }
}
