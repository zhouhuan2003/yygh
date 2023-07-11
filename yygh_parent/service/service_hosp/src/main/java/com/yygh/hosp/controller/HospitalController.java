package com.yygh.hosp.controller;/*
 *@author 周欢
 *@version 1.0
 */

import com.yygh.common.result.Result;
import com.yygh.hosp.service.HospitalService;
import com.yygh.hosp.service.HospitalSetService;
import com.yygh.model.hosp.Hospital;
import com.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
@Api(tags = "医院接口")
public class HospitalController {

    @Resource
    private HospitalService hospitalService;

    @ApiOperation("医院列表")
    @GetMapping("/list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page, @PathVariable Integer limit, HospitalQueryVo hospitalQueryVo){

        Page<Hospital> pageModel=hospitalService.selectHospPage(page,limit,hospitalQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation("更新上线状态")
    @GetMapping("/updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id,@PathVariable Integer status){

        hospitalService.updateStatus(id,status);

        return Result.ok();

    }

    @ApiOperation("医院详情信息")
    @GetMapping("/showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id){
        Map<String,Object> map=hospitalService.getHospById(id);
        return Result.ok(map);
    }

}
