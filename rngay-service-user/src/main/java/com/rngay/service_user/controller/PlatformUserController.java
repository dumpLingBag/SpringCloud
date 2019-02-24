package com.rngay.service_user.controller;

import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.*;
import com.rngay.service_user.service.UAUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class PlatformUserController {

    @Resource
    private UAUserService uaUserService;
    @Resource
    private Dao dao;

    @RequestMapping(value = "batch")
    public Result<?> batch() {
        List<UAIconDTO> user = new ArrayList<>();
        UAIconDTO iconDTO = new UAIconDTO();
        iconDTO.setIcon("qqq");
        iconDTO.setText("www");
        user.add(iconDTO);
        UAIconDTO dto = new UAIconDTO();
        dto.setIcon("rrr");
        dto.setText("ppp");
        user.add(dto);
        int[] ints = dao.batchInsert(user);
        return Result.success(ints);
    }

    @RequestMapping(value = "find")
    public Result<UAUserDTO> find(@RequestParam("account") String account, @RequestParam("password") String password){
        return Result.success(uaUserService.findUser(account, password));
    }

    @RequestMapping(value = "findById/{id}", method = RequestMethod.GET)
    public Result<UAUserDTO> findById(@PathVariable Integer id){
        return Result.success(uaUserService.findUserById(id));
    }

    @RequestMapping(value = "findByAccount", method = RequestMethod.POST)
    public Result<UAUserDTO> findByAccount(@RequestParam("account") String account){
        return Result.success(uaUserService.findByAccount(account));
    }

    @RequestMapping(value = "findByMobile", method = RequestMethod.POST)
    public Result<UAUserDTO> findByMobile(@RequestParam("mobile") String mobile){
        return Result.success(uaUserService.findByMobile(mobile));
    }

    @RequestMapping(value = "save")
    public Result<Integer> save(@RequestBody UASaveUserDTO saveUserDTO){
        saveUserDTO.setCreate_time(new Date());
        saveUserDTO.setUpdate_time(new Date());
        return Result.success(uaUserService.insertUser(saveUserDTO));
    }

    @RequestMapping(value = "update")
    public Result<Integer> update(@RequestBody UAUpdateUserDTO updateUserDTO){
        return Result.success(uaUserService.updateUser(updateUserDTO));
    }

    @RequestMapping(value = "pageList")
    public Result<PageList<UAUserDTO>> pageList(@RequestBody UAUserPageListDTO pageListDTO){
        return Result.success(uaUserService.pageList(pageListDTO));
    }

    @RequestMapping(value = "reset/{id}")
    public Result<Integer> reset(@PathVariable Integer id){
        return Result.success(uaUserService.reset(id));
    }

    @RequestMapping(value = "enable/{id}/{enable}")
    public Result<Integer> enable(@PathVariable Integer id, @PathVariable Integer enable){
        return Result.success(uaUserService.enable(id, enable));
    }

    @RequestMapping(value = "updatePassword")
    public Result<Integer> updatePassword(@RequestBody UpdatePassword password){
        return Result.success(uaUserService.updatePassword(password));
    }

    @RequestMapping(value = "delete/{id}")
    public Result<Integer> delete(@PathVariable Integer id){
        return Result.success(uaUserService.delete(id));
    }

}
