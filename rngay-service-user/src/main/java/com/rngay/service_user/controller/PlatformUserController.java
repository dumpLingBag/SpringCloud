package com.rngay.service_user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.user.dto.*;
import com.rngay.service_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class PlatformUserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "find")
    public Result<UaUserDTO> find(@RequestParam("username") String account, @RequestParam("password") String password) {
        return Result.success(userService.findUser(account, password));
    }

    @GetMapping(value = "findById/{id}")
    public Result<UaUserDTO> findById(@PathVariable Long id) {
        return Result.success(userService.findUserById(id));
    }

    @GetMapping(value = "findByAccount")
    public Result<UaUserDTO> findByAccount(@RequestParam("username") String username) {
        return Result.success(userService.findByAccount(username));
    }

    @GetMapping(value = "findByMobile")
    public Result<UaUserDTO> findByMobile(@RequestParam("mobile") String mobile) {
        return Result.success(userService.findByMobile(mobile));
    }

    @PostMapping(value = "save")
    public Result<Integer> save(@RequestBody UaUserDTO saveUserDTO) {
        saveUserDTO.setPassword(BCrypt.hashpw(saveUserDTO.getPassword(), BCrypt.gensalt(12)));
        saveUserDTO.setCreateTime(new Date());
        saveUserDTO.setUpdateTime(new Date());
        return Result.success(userService.insertUser(saveUserDTO));
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@RequestBody UaUserDTO updateUserDTO) {
        return Result.success(userService.updateUser(updateUserDTO));
    }

    @PostMapping(value = "pageList")
    public Result<Page<UaUserDTO>> pageList(@RequestBody UaUserPageListDTO pageListDTO) {
        return Result.success(userService.pageList(pageListDTO));
    }

    @PutMapping(value = "reset/{id}")
    public Result<String> reset(@PathVariable Long id) {
        return Result.success(userService.reset(id));
    }

    @PutMapping(value = "enable/{id}/{enable}")
    public Result<Integer> enable(@PathVariable Long id, @PathVariable Integer enable) {
        return Result.success(userService.enable(id, enable));
    }

    @PutMapping(value = "updatePassword")
    public Result<Integer> updatePassword(@RequestBody UpdatePassword password) {
        password.setPassword(BCrypt.hashpw(password.getPassword(), BCrypt.gensalt(12)));
        return Result.success(userService.updatePassword(password));
    }

    @DeleteMapping(value = "delete/{id}")
    public Result<Integer> delete(@PathVariable Long id) {
        return Result.success(userService.delete(id));
    }

    @PostMapping(value = "uploadAvatar")
    public Result<Integer> uploadAvatar(@RequestParam("path") String path, @RequestParam("userId") Long userId) {
        return Result.success(userService.uploadAvatar(path, userId));
    }

    @PostMapping(value = "loadByUserIds")
    public Result<List<UaUserDTO>> loadByUserIds(@RequestBody List<UserRoleDTO> roleDTO) {
        return Result.success(userService.loadByUserIds(roleDTO));
    }

}
