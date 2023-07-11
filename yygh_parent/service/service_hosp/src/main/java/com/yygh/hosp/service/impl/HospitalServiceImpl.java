package com.yygh.hosp.service.impl;/*
 *@author 周欢
 *@version 1.0
 */

import com.alibaba.fastjson.JSONObject;
import com.cmn.client.DictFeignClient;
import com.yygh.hosp.repository.HospitalRepository;
import com.yygh.hosp.service.HospitalService;
import com.yygh.model.hosp.Hospital;
import com.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Resource
    private HospitalRepository hospitalRepository;

    @Resource
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> map) {
        //把map集合转换为对象
        String mapStr = JSONObject.toJSONString(map);
        Hospital hospital = JSONObject.parseObject(mapStr, Hospital.class);
        //判断是否存在相同数据
        String hoscode = hospital.getHoscode();
        Hospital hospital1Exist=hospitalRepository.getHospitalByHoscode(hoscode);

        //如果存在，进行修改
        if (hospital1Exist!=null){
            hospital.setStatus(hospital1Exist.getStatus());
            hospital.setCreateTime(hospital1Exist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else{
        //如果不存在，进行添加
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospitalByHoscode = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospitalByHoscode;
    }

    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        //创建pageable对象
        Pageable pageable= PageRequest.of(page-1,limit);

        //组织条件匹配器
        ExampleMatcher matching = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);

        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);
        Example<Hospital> example = Example.of(hospital, matching);
        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);

        //获取查询list集合，遍历进行医院等级的封装
        pages.getContent().stream().forEach(item ->{
            this.setHospitalHosType(item);
        });

        return pages;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public Map<String,Object> getHospById(String id) {
        HashMap<String, Object> map = new HashMap<>();
        Hospital hospital = hospitalRepository.findById(id).get();
        Hospital hospital1 = this.setHospitalHosType(hospital);
        //医院的基本信息
        map.put("hospital",hospital1);
        //单独处理更直观
        map.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital1.setBookingRule(null);
        return map;

    }

    @Override
    public String getHospName(String hoscode) {
        Hospital hospitalByHoscode = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospitalByHoscode!=null){
            return hospitalByHoscode.getHosname();
        }else {
            return null;
        }
    }

    @Override
    public List<Hospital> findByHosname(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        HashMap<String, Object> map = new HashMap<>();
        Hospital hospital = this.setHospitalHosType(this.getByHoscode(hoscode));
        map.put("hospital",hospital);
        map.put("bookingRule",hospital.getBookingRule());
        hospital.setBookingRule(null);
        return map;
    }

    private Hospital setHospitalHosType(Hospital hospital) {
        String hostype = dictFeignClient.getName("Hostype", hospital.getHostype());

        //查询省 市 地区
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("fullAddress",provinceString+cityString+districtString);
        hospital.getParam().put("hostypeString",hostype);
        return hospital;
    }


}
