package com.rngay.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.user.dto.*;
import com.rngay.user.service.UserService;
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

    @GetMapping(value = "getUser")
    public Result<UaUserDTO> getUser(@RequestParam("username") String account, @RequestParam("password") String password) {
        return Result.success(userService.getUser(account, password));
    }

    @GetMapping(value = "getUserById/{id}")
    public Result<UaUserDTO> getUserById(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    @GetMapping(value = "getUserByUsername")
    public Result<UaUserDTO> getUserByUsername(@RequestParam("username") String username) {
        return Result.success(userService.getUserByUsername(username));
    }

    @GetMapping(value = "getUserByMobile")
    public Result<UaUserDTO> getUserByMobile(@RequestParam("mobile") String mobile) {
        return Result.success(userService.getUserByMobile(mobile));
    }

    @PostMapping(value = "insert")
    public Result<Integer> insert(@RequestBody UaUserDTO saveUserDTO) {
        saveUserDTO.setPassword(BCrypt.hashpw(saveUserDTO.getPassword(), BCrypt.gensalt(12)));
        saveUserDTO.setCreateTime(new Date());
        saveUserDTO.setUpdateTime(new Date());
        return Result.success(userService.insertUser(saveUserDTO));
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@RequestBody UaUserDTO updateUserDTO) {
        return Result.success(userService.updateUser(updateUserDTO));
    }

    @PostMapping(value = "page")
    public Result<Page<UaUserDTO>> page(@RequestBody UaUserPageListDTO pageListDTO) {
        return Result.success(userService.page(pageListDTO));
    }

    @PutMapping(value = "reset/{id}")
    public Result<String> reset(@PathVariable Long id) {
        return Result.success(userService.reset(id));
    }

    @PutMapping(value = "enabled/{id}/{enabled}")
    public Result<Integer> enabled(@PathVariable Long id, @PathVariable Integer enabled) {
        return Result.success(userService.enabled(id, enabled));
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
