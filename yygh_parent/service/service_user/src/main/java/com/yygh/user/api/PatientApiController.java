package com.yygh.user.api;/*
 *@author 周欢
 *@version 1.0
 */

import com.sun.xml.internal.ws.resources.HttpserverMessages;
import com.yygh.common.result.Result;
import com.yygh.common.utils.AuthContextHolder;
import com.yygh.model.user.Patient;
import com.yygh.user.service.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {

    @Resource
    private PatientService patientService;

    //获取就诊人列表
    @GetMapping("/auth/findAll")
    public Result findAll(HttpServletRequest request){
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        List<Patient> list=patientService.findAllUserId(userId);
        return Result.ok(list);
    }

    //添加就诊人
    @PostMapping("/auth/save")
    public Result savePatient(@RequestBody Patient patient,HttpServletRequest request){
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        patientService.save(patient);
        return Result.ok();
    }

    //根据id查询就诊人信息
    @GetMapping("/auth/get/{id}")
    public Result getPatient(@PathVariable Integer id){
        Patient patient=patientService.getPatientId(id);
        return Result.ok(patient);
    }

    //修改就诊人
    @PutMapping("/auth/update")
    public Result updatePatient(@RequestBody Patient patient){
        patientService.updateById(patient);
        return Result.ok();
    }

    //删除就诊人
    @DeleteMapping("/auth/remove/{id}")
    public Result removePatient(@PathVariable Integer id){
        patientService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "获取就诊人")
    @GetMapping("inner/get/{id}")
    public Patient getPatientOrder(
            @ApiParam(name = "id", value = "就诊人id", required = true)
            @PathVariable("id") Integer id) {
        return patientService.getPatientId(id);
    }
}
