package com.yygh.msm.service;/*
 *@author 周欢
 *@version 1.0
 */

import com.yygh.vo.msm.MsmVo;

public interface MsmService {
    //发送手机验证码
    boolean send(String phone, String code);

    boolean send(MsmVo msmVo);
}
