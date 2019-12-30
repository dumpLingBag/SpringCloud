package com.rngay.service_user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.*;
import com.rngay.service_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "user")
public class PlatformUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "find", method = RequestMethod.GET)
    public Result<UAUserDTO> find(@RequestParam("username") String account, @RequestParam("password") String password) {
        return Result.success(userService.findUser(account, password));
    }

    @RequestMapping(value = "findById/{id}", method = RequestMethod.GET)
    public Result<UAUserDTO> findById(@PathVariable Long id) {
        return Result.success(userService.findUserById(id));
    }

    @RequestMapping(value = "findByAccount", method = RequestMethod.GET)
    public Result<UAUserDTO> findByAccount(@RequestParam("username") String account) {
        return Result.success(userService.findByAccount(account));
    }

    @RequestMapping(value = "findByMobile", method = RequestMethod.GET)
    public Result<UAUserDTO> findByMobile(@RequestParam("mobile") String mobile) {
        return Result.success(userService.findByMobile(mobile));
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result<Integer> save(@RequestBody UAUserDTO saveUserDTO) {
        saveUserDTO.setPassword(BCrypt.hashpw(saveUserDTO.getPassword(), BCrypt.gensalt(12)));
        saveUserDTO.setCreateTime(new Date());
        saveUserDTO.setUpdateTime(new Date());
        return Result.success(userService.insertUser(saveUserDTO));
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result<Integer> update(@RequestBody UAUserDTO updateUserDTO) {
        return Result.success(userService.updateUser(updateUserDTO));
    }

    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    public Result<Page<UAUserDTO>> pageList(@RequestBody UAUserPageListDTO pageListDTO) {
        return Result.success(userService.pageList(pageListDTO));
    }

    @RequestMapping(value = "reset/{id}", method = RequestMethod.PUT)
    public Result<Integer> reset(@PathVariable Long id) {
        return Result.success(userService.reset(id));
    }

    @RequestMapping(value = "enable/{id}/{enable}", method = RequestMethod.PUT)
    public Result<Integer> enable(@PathVariable Long id, @PathVariable Integer enable) {
        return Result.success(userService.enable(id, enable));
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.PUT)
    public Result<Integer> updatePassword(@RequestBody UpdatePassword password) {
        password.setPassword(BCrypt.hashpw(password.getPassword(), BCrypt.gensalt(12)));
        return Result.success(userService.updatePassword(password));
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public Result<Integer> delete(@PathVariable Long id) {
        return Result.success(userService.delete(id));
    }

}
