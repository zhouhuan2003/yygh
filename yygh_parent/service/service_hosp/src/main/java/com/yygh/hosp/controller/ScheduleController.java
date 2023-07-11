package com.yygh.hosp.controller;/*
 *@author 周欢
 *@version 1.0
 */

import com.yygh.common.result.Result;
import com.yygh.hosp.service.ScheduleService;
import com.yygh.model.hosp.Schedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/schedule")
//@CrossOrigin
@Api(tags = "排班设置")
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    @ApiOperation("根据医院编号 和 科室编号，查询排班规则的数据")
    @GetMapping("/getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable Integer page,@PathVariable Integer limit,@PathVariable String hoscode,@PathVariable String depcode){
        Map<String,Object> map=scheduleService.getRuleSchedule(page,limit,hoscode,depcode);
        return Result.ok(map);
    }

    @ApiOperation("查询排班详细信息")
    @GetMapping("/getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String hoscode,@PathVariable String depcode,@PathVariable String workDate){

        List<Schedule> list=scheduleService.getScheduleDetail(hoscode,depcode,workDate);

        return Result.ok(list);
    }
}
