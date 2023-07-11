package com.yygh.hosp.service;/*
 *@author 周欢
 *@version 1.0
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yygh.model.hosp.HospitalSet;
import com.yygh.vo.hosp.HospitalSetQueryVo;
import com.yygh.vo.order.SignInfoVo;

public interface HospitalSetService extends IService<HospitalSet> {

    IPage<HospitalSet> selectByPage(Integer pageNum, Integer pageSize, HospitalSetQueryVo hospitalSetQueryVo);

    String getSingKey(String hoscode);

    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
