package com.yygh.hosp.repository;/*
 *@author 周欢
 *@version 1.0
 */

import com.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);

    List<Department> getDepartmentByHoscode(String hoscode);
}
