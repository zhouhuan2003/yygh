package com.eazyexcel;/*
 *@author 周欢
 *@version 1.0
 */

import com.alibaba.excel.EasyExcel;
import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args) {

        List<UserData> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserData userData = new UserData();
            userData.setId(i);
            userData.setUsername("lucy"+i);
            list.add(userData);
        }

        //设置excel文件名称和路径
        String fileName="E:\\excel\\01.xlsx";

        //调用方法实现
        EasyExcel.write(fileName,UserData.class).sheet("用户的消息")
                .doWrite(list);
    }
}
