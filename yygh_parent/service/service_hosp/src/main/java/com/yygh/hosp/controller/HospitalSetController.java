package com.yygh.hosp.controller;/*
 *@author 周欢
 *@version 1.0
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yygh.common.exception.YyghException;
import com.yygh.common.result.MD5;
import com.yygh.common.result.Result;
import com.yygh.hosp.service.HospitalSetService;
import com.yygh.model.hosp.HospitalSet;
import com.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
//@CrossOrigin
public class HospitalSetController {

    @Resource
    private HospitalSetService hospitalSetService;

    //查询
    @ApiOperation(value = "获取所有的医院设置的信息")
    @GetMapping("/getAll")
    public Result getAll(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //根据id删除
    @ApiOperation(value = "删除医院的设置")
    @DeleteMapping("/delete/{id}")
    public Result deleteHospSet(@PathVariable("id")Integer id){
        boolean b = hospitalSetService.removeById(id);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }

    }

    @ApiOperation("条件查询带分页")
    //条件查询带分页
    @PostMapping("/selectByPage/{PageNum}/{PageSize}")
    public Result selectByPage(@PathVariable Integer PageNum, @PathVariable Integer PageSize, @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        IPage<HospitalSet> iPage=hospitalSetService.selectByPage(PageNum,PageSize,hospitalSetQueryVo);
        return Result.ok(iPage);
    }

    @ApiOperation("添加医院设置")
    @PostMapping("/save")
    public Result save(@RequestBody HospitalSet hospitalSet){
        //设置状态 1使用，0不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if (save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("根据id获取医院设置")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable String id){
        HospitalSet byId = hospitalSetService.getById(id);
        return Result.ok(byId);
    }

    @ApiOperation("修改医院设置")
    @PostMapping("/update")
    public Result update(@RequestBody HospitalSet hospitalSet){
        boolean b = hospitalSetService.updateById(hospitalSet);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("批量删除医院设置")
    @DeleteMapping("/deleteByIds")
    public Result delete(@RequestBody List<Integer> id){
        boolean b = hospitalSetService.removeByIds(id);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("医院设置锁和解锁")
    @PutMapping("/lock/{id}/{status}")
    public Result lock(@PathVariable Integer id,@PathVariable Integer status){
        HospitalSet byId = hospitalSetService.getById(id);
        byId.setStatus(status);
        boolean b = hospitalSetService.updateById(byId);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("发送签名秘钥")
    @PutMapping("/sendKey/{id}")
    public Result lock(@PathVariable Integer id){
        HospitalSet byId = hospitalSetService.getById(id);
        String signKey = byId.getSignKey();
        return Result.ok(signKey);
    }
}
