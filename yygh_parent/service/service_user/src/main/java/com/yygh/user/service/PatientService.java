package com.yygh.user.service;/*
 *@author 周欢
 *@version 1.0
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.yygh.model.user.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PatientService extends IService<Patient> {
    List<Patient> findAllUserId(Long userId);

    Patient getPatientId(Integer id);
}
