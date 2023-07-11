package com.yygh.cmn.controller;/*
 *@author 周欢
 *@version 1.0
 */

import com.yygh.cmn.service.DictService;
import com.yygh.common.result.Result;
import com.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/admin/cmn/dict")
@Api(value = "数据字典")
//@CrossOrigin
public class DictController {

    @Resource
    private DictService dictService;

//    @GetMapping("/findByParentId/{parentId}")
//    public Result findByParentId(@PathVariable Integer parentId){
//        List<Dict> list=dictService.findByParent(parentId);
//        return Result.ok(list);
//    }

    @ApiOperation("导入数据字典")
    @PostMapping("/importData")
    public Result importDict(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();
    }

    @ApiOperation("导出数据字典")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        dictService.exportDictData(response);
    }

    @ApiOperation("根据dictCode查询下级节点")
    @GetMapping("/findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode){
        List<Dict> list=dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }


    @ApiOperation("根据id查询子数据列表")
    @GetMapping("/findChildData/{id}")
    public Result findChildData(@PathVariable Integer id){
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }

    //根据dictcode和value值查询
    @GetMapping("/getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,@PathVariable String value){
        String DictName=dictService.getDictName(dictCode,value);
        return DictName;
    }

    //根据value值查询
    @GetMapping("/getName/{value}")
    public String getName(@PathVariable String value){
        String DictName=dictService.getDictName("",value);
        return DictName;
    }


}
