package com.yygh.user.service.impl;/*
 *@author 周欢
 *@version 1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmn.client.DictFeignClient;
import com.yygh.enums.DictEnum;
import com.yygh.model.user.Patient;
import com.yygh.user.mapper.PatientMapper;
import com.yygh.user.service.PatientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    @Resource
    private DictFeignClient dictFeignClient;

    @Override
    public List<Patient> findAllUserId(Long userId) {
        //根据userid查询所有的就诊人信息列表
        QueryWrapper<Patient> patientQueryWrapper = new QueryWrapper<>();
        patientQueryWrapper.eq("user_id",userId);
        List<Patient> patients = baseMapper.selectList(patientQueryWrapper);
        //通过远程调用，得到编号码对应的具体内容
        patients.stream().forEach(item->{
            this.packPatient(item);
        });
        return patients;
    }

    @Override
    public Patient getPatientId(Integer id) {
        Patient patient = baseMapper.selectById(id);
        return this.packPatient(patient);
    }

    //Patient其它参数的封装
    private Patient packPatient(Patient patient) {
        String certificatesTypeString = dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getCertificatesType());
        //联系人证件类型
        String contactsCertificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(),patient.getContactsCertificatesType());
        //省
        String provinceString = dictFeignClient.getName(patient.getProvinceCode());
        //市
        String cityString = dictFeignClient.getName(patient.getCityCode());
        //区
        String districtString = dictFeignClient.getName(patient.getDistrictCode());
        patient.getParam().put("certificatesTypeString", certificatesTypeString);
        patient.getParam().put("contactsCertificatesTypeString", contactsCertificatesTypeString);
        patient.getParam().put("provinceString", provinceString);
        patient.getParam().put("cityString", cityString);
        patient.getParam().put("districtString", districtString);
        patient.getParam().put("fullAddress", provinceString + cityString + districtString + patient.getAddress());
        return patient;
    }
}
