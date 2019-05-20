package com.rngay.service_user.controller;

import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.*;
import com.rngay.service_user.service.UAUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class PlatformUserController {

    @Autowired
    private UAUserService uaUserService;

    @RequestMapping(value = "find", method = RequestMethod.POST)
    public Result<UAUserDTO> find(@RequestParam("account") String account, @RequestParam("password") String password) {
        return Result.success(uaUserService.findUser(account, password));
    }

    @RequestMapping(value = "findById/{id}", method = RequestMethod.GET)
    public Result<UAUserDTO> findById(@PathVariable Integer id) {
        return Result.success(uaUserService.findUserById(id));
    }

    @RequestMapping(value = "findByAccount", method = RequestMethod.GET)
    public Result<UAUserDTO> findByAccount(@RequestParam("account") String account) {
        return Result.success(uaUserService.findByAccount(account));
    }

    @RequestMapping(value = "findByMobile", method = RequestMethod.GET)
    public Result<UAUserDTO> findByMobile(@RequestParam("mobile") String mobile) {
        return Result.success(uaUserService.findByMobile(mobile));
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result<Integer> save(@RequestBody UASaveUserDTO saveUserDTO) {
        saveUserDTO.setPassword(BCrypt.hashpw(saveUserDTO.getPassword(), BCrypt.gensalt(12)));
        saveUserDTO.setCreate_time(new Date());
        saveUserDTO.setUpdate_time(new Date());
        return Result.success(uaUserService.insertUser(saveUserDTO));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result<Integer> update(@RequestBody UAUpdateUserDTO updateUserDTO) {
        return Result.success(uaUserService.updateUser(updateUserDTO));
    }

    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    public Result<PageList<UAUserDTO>> pageList(@RequestBody UAUserPageListDTO pageListDTO) {
        return Result.success(uaUserService.pageList(pageListDTO));
    }

    @RequestMapping(value = "reset/{id}", method = RequestMethod.GET)
    public Result<Integer> reset(@PathVariable Integer id) {
        return Result.success(uaUserService.reset(id));
    }

    @RequestMapping(value = "enable/{id}/{enable}", method = RequestMethod.GET)
    public Result<Integer> enable(@PathVariable Integer id, @PathVariable Integer enable) {
        return Result.success(uaUserService.enable(id, enable));
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Result<Integer> updatePassword(@RequestBody UpdatePassword password) {
        password.setPassword(BCrypt.hashpw(password.getPassword(), BCrypt.gensalt(12)));
        return Result.success(uaUserService.updatePassword(password));
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public Result<Integer> delete(@PathVariable Integer id) {
        return Result.success(uaUserService.delete(id));
    }

    @RequestMapping(value = "userIdForList", method = RequestMethod.POST)
    public Result<List<UAUserDTO>> userIdForList(@RequestParam("userList") List<String> userList) {
        return Result.success(uaUserService.userIdForList(userList));
    }

}
