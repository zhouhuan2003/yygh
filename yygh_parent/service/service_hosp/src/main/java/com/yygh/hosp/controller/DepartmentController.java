package com.yygh.hosp.controller;/*
 *@author 周欢
 *@version 1.0
 */

import com.yygh.common.result.Result;
import com.yygh.hosp.service.DepartmentService;
import com.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/hosp/department")
//@CrossOrigin
@Api(tags = "医院排班设置")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @ApiOperation("根据医院编号，查询医院所有的科室列表")
    @GetMapping("/getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode){
        List<DepartmentVo> list=departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }
}
