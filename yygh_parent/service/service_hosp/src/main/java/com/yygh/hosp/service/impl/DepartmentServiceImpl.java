package com.yygh.hosp.service.impl;/*
 *@author 周欢
 *@version 1.0
 */

import com.alibaba.fastjson.JSONObject;
import com.yygh.hosp.repository.DepartmentRepository;
import com.yygh.hosp.service.DepartmentService;
import com.yygh.model.hosp.Department;
import com.yygh.vo.hosp.DepartmentQueryVo;
import com.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentRepository departmentRepository;

    //上传科室
    @Override
    public void save(Map<String, Object> map) {
        //把map转成对象
        String string = JSONObject.toJSONString(map);
        Department department= JSONObject.parseObject(string,Department.class);

        Department departmentExist=departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(),department.getDepcode());

        //判断
        if (departmentExist!=null){
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        }else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        Pageable pageable= PageRequest.of(page-1,limit);

        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        department.setIsDeleted(0);

        ExampleMatcher matching =  ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example=Example.of(department,matching);
        return departmentRepository.findAll(example,pageable);
    }

    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号 和 科室编号查询
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);

        if (department!=null){
            departmentRepository.deleteById(department.getId());
        }

    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建list集合，用于最终封装数据
        ArrayList<DepartmentVo> departmentVos = new ArrayList<>();
        //根据医院编号，查询医院所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);

        Example<Department> e = Example.of(departmentQuery);
        //所有科室
        List<Department> all = departmentRepository.findAll(e);

        //根据大科室分组，获取每个大科室的子科室
        Map<String, List<Department>> departmentMap = all.stream().collect(Collectors.groupingBy(Department::getBigcode));
        for (Map.Entry<String,List<Department>> entry: departmentMap.entrySet()) {
            //大科室编号
            String bigcode = entry.getKey();
            //大科室编号对应的数据
            List<Department> departmentList = entry.getValue();

            //封装大科室
            DepartmentVo departmentVo=new DepartmentVo();
            departmentVo.setDepcode(bigcode);
            departmentVo.setDepname(departmentList.get(0).getBigname());

            //封装小科室
            ArrayList<DepartmentVo> children = new ArrayList<>();
            for (Department department: departmentList){
                DepartmentVo departmentVo1 = new DepartmentVo();
                departmentVo1.setDepcode(department.getDepcode());
                departmentVo1.setDepname(department.getDepname());
                //封装到list集合
                children.add(departmentVo1);
            }
            //把小科室的集合放到大科室的children里面
            departmentVo.setChildren(children);
            //放到最终的departmentVos
            departmentVos.add(departmentVo);

        }
        return departmentVos;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        Department departmentByHoscodeAndDepcode = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (departmentByHoscodeAndDepcode!=null){
            return departmentByHoscodeAndDepcode.getDepname();
        }
        return null;
    }

    @Override
    public List<Department> findDepartmentByHoscode(String hoscode) {
        return departmentRepository.getDepartmentByHoscode(hoscode);
    }

    @Override
    public Department getDepartment(String hoscode, String depcode) {
        return departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);

    }
}
