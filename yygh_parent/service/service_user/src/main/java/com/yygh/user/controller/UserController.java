package com.yygh.user.controller;/*
 *@author 周欢
 *@version 1.0
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yygh.common.result.Result;
import com.yygh.model.user.UserInfo;
import com.yygh.user.service.UserInfoService;
import com.yygh.vo.user.UserInfoQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    private UserInfoService userInfoService;

    //用户列表，带分页条件查询
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Integer page, @PathVariable Integer limit, UserInfoQueryVo userInfoQueryVo){
        IPage<UserInfo> page1= userInfoService.selectPage(page,limit,userInfoQueryVo);
        return Result.ok(page1);
    }

    //用户锁定
    @GetMapping("lock/{userId}/{status}")
    public Result lock(@PathVariable Integer userId,@PathVariable Integer status){
        userInfoService.lock1(userId,status);
        return Result.ok();
    }

    //查询用户详情
    @GetMapping("show/{userId}")
    public Result show(@PathVariable Integer userId){
        Map<String,Object> map=userInfoService.showByUserId(userId);
        return Result.ok(map);
    }

    //认证审批
    @GetMapping("approval/{userId}/{authStatus}")
    public Result approval(@PathVariable Long userId,@PathVariable Integer authStatus) {
        userInfoService.approval(userId,authStatus);
        return Result.ok();
    }
}
