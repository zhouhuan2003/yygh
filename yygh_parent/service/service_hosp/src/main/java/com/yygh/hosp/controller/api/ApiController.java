package com.yygh.hosp.controller.api;/*
 *@author 周欢
 *@version 1.0
 */

import com.yygh.common.exception.YyghException;
import com.yygh.common.helper.HttpRequestHelper;
import com.yygh.common.result.Result;
import com.yygh.common.result.ResultCodeEnum;
import com.yygh.common.utils.MD5;
import com.yygh.hosp.service.DepartmentService;
import com.yygh.hosp.service.HospitalService;
import com.yygh.hosp.service.HospitalSetService;
import com.yygh.hosp.service.ScheduleService;
import com.yygh.model.hosp.Department;
import com.yygh.model.hosp.Hospital;
import com.yygh.model.hosp.Schedule;
import com.yygh.vo.hosp.DepartmentQueryVo;
import com.yygh.vo.hosp.DepartmentVo;
import com.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Resource
    private HospitalService hospitalService;

    @Resource
    private HospitalSetService hospitalSetService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ScheduleService scheduleService;

    //删除排班
    @PostMapping("/schedule/remove")
    public Result removeSchedule(HttpServletRequest request){
        //获取消息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        String hoscode = (String) map.get("hoscode");
        String hosScheduleId = (String) map.get("hosScheduleId");

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    //查询排班接口
    @PostMapping("/schedule/list")
    public Result findSchedule(HttpServletRequest request){
        //获取消息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        String hoscode = (String) map.get("hoscode");

        String depcode = (String) map.get("depcode");

        int page= StringUtils.isEmpty(map.get("page"))? 1: Integer.parseInt((String)map.get("page"));
        int limit= StringUtils.isEmpty(map.get("limit"))? 1: Integer.parseInt((String)map.get("limit"));
        //TODO 签名验证

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        Page<Schedule> pageModel=scheduleService.findPageSchedule(page,limit,scheduleQueryVo);

        return Result.ok(pageModel);
    }

    //上传排班
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取消息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        scheduleService.save(map);
        return Result.ok();

    }


    //删除科室接口
    @PostMapping("/department/remove")
    public Result removeDepartment(HttpServletRequest request){
        //获取消息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        String hoscode = (String) map.get("hoscode");
        String depcode = (String) map.get("depcode");

        departmentService.remove(hoscode,depcode);
        return Result.ok();

    }

    //查询科室的接口
    @PostMapping("/department/list")
    public Result findDepartment(HttpServletRequest request){
        //获取消息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);


        String hoscode = (String) map.get("hoscode");

        int page= StringUtils.isEmpty(map.get("page"))? 1: Integer.parseInt((String)map.get("page"));
        int limit= StringUtils.isEmpty(map.get("limit"))? 1: Integer.parseInt((String)map.get("limit"));
        //TODO 签名验证

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        Page<Department> pageModel=departmentService.findPageDepartment(page,limit,departmentQueryVo);

        return Result.ok(pageModel);
    }

    //上传科室接口
    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        //获取消息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        //获取传递过来的编号
        String hoscode = (String) map.get("hoscode");

        //1 获取医院系统传递过来的签名
        String hospSign = (String) map.get("sign");

        //2 根据医院编号查询数据库签名
        String singKey=hospitalSetService.getSingKey(hoscode);
        String singKeyMD5 = MD5.encrypt(singKey);

        //判断
        if (!hospSign.equals(singKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service方法实现
        departmentService.save(map);


        return Result.ok();
    }


    //查询医院
    @PostMapping("/hospital/show")
    public Result getHospital(HttpServletRequest request){
        //获取消息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        //获取传递过来的编号
        String hoscode = (String) map.get("hoscode");

        //1 获取医院系统传递过来的签名
        String hospSign = (String) map.get("sign");

        //2 根据医院编号查询数据库签名
        String singKey=hospitalSetService.getSingKey(hoscode);
        String singKeyMD5 = MD5.encrypt(singKey);

        //判断
        if (!hospSign.equals(singKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service方法实现根据医院编号查询
        Hospital hospital=hospitalService.getByHoscode(hoscode);

        return Result.ok(hospital);
    }

    //上传医院的接口
    @PostMapping("/saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取消息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);

        //1 获取医院系统传递过来的签名
        String hospSign = (String) map.get("sign");

        //2 根据医院编号查询数据库签名
        String hoscode = (String) map.get("hoscode");
        String singKey=hospitalSetService.getSingKey(hoscode);
        String singKeyMD5 = MD5.encrypt(singKey);

        //判断
        if (hospSign.equals(singKeyMD5)){
            //
            String logoData = (String) map.get("logoData");
            logoData=logoData.replaceAll(" ","+");
            map.put("logoData",logoData);

            hospitalService.save(map);
            return Result.ok();
        }else {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
    }



}
