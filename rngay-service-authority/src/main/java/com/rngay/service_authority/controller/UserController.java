package com.rngay.service_authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.util.BuilderUtil;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.*;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "user", name = "用户管理")
public class UserController {

    @Autowired
    private PFUserService pfUserService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "save", method = RequestMethod.POST, name = "保存用户")
    public Result<?> save(@Valid @RequestBody UAUserDTO saveUserDTO) {
        Map<String, String> msg = new HashMap<>();
        Result<UAUserDTO> byAccount = pfUserService.findByAccount(saveUserDTO.getUsername());
        if (byAccount.getData() != null) {
            msg.put("username", "此账号名称已经存在");
        }
        Result<UAUserDTO> byMobile = pfUserService.findByMobile(saveUserDTO.getMobile());
        if (byMobile.getData() != null) {
            msg.put("mobile", "此手机号码已经存在");
        }
        if (!msg.isEmpty()) {
            return Result.fail(msg);
        }
        return pfUserService.save(saveUserDTO);
    }

    @RequestMapping(value = "pageList", method = RequestMethod.POST, name = "分页展示用户")
    public Result<Page<UAUserDTO>> pageList(@RequestBody UAUserPageListDTO pageListDTO) {
        return pfUserService.pageList(pageListDTO);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "更新用户")
    public Result<?> update(@Valid @RequestBody UAUserDTO updateUserDTO) {
        UAUserDTO user = pfUserService.findById(updateUserDTO.getId()).getData();
        if (user != null) {
            if (user.getUsername().equals("admin") && !updateUserDTO.getUsername().equals("admin")) {
                return Result.failMsg("禁止修改管理员账户名称");
            }
            if (!user.getUsername().equals(updateUserDTO.getUsername())) {
                Result<UAUserDTO> byAccount = pfUserService.findByAccount(updateUserDTO.getUsername());
                if (byAccount.getData() != null) {
                    BuilderUtil.builder("username", "此账号名称已经存在");
                }
            }
            if (!user.getMobile().equals(updateUserDTO.getMobile())) {
                Result<UAUserDTO> byMobile = pfUserService.findByMobile(updateUserDTO.getMobile());
                if (byMobile.getData() != null) {
                    BuilderUtil.builder("mobile", "此手机号码已经存在");
                }
            }
            if (BuilderUtil.getLength()) {
                return Result.fail(BuilderUtil.getBuilder());
            }
        } else {
            return Result.failMsg("不存在该用户");
        }
        return pfUserService.update(updateUserDTO);
    }

    @RequestMapping(value = "reset/{id}", method = RequestMethod.GET, name = "重置密码")
    public Result<Integer> reset(@PathVariable Long id) {
        if (id == null) {
            return Result.failMsg("重置失败");
        }
        return pfUserService.reset(id);
    }

    @RequestMapping(value = "enable/{id}/{enable}", method = RequestMethod.GET, name = "启用禁用")
    public Result<Integer> enable(@PathVariable Long id, @PathVariable Integer enable) {
        UAUserDTO currentUser = systemService.getCurrentUser(request);
        if (currentUser.getUsername().equals("admin") && currentUser.getId().equals(id)) {
            return Result.failMsg("禁止禁用管理员");
        }
        if (id != null && enable != null) {
            return pfUserService.enable(id, enable);
        } else {
            return Result.failMsg("操作失败，请联系管理员");
        }
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST, name = "修改密码")
    public Result<Integer> updatePassword(HttpServletRequest request, @RequestBody UpdatePassword password) {
        Long userId = systemService.getCurrentUserId(request);
        UAUserDTO user = pfUserService.findById(userId).getData();
        if (user == null) {
            return Result.failMsg("不存在该用户，修改失败");
        }
        if (!BCrypt.checkpw(password.getOldPassword(), user.getPassword())) {
            return Result.failMsg("旧密码输入不正确");
        }
        password.setUserId(userId);
        return pfUserService.updatePassword(password);
    }

    @RequestMapping(value = "checkPassword", method = RequestMethod.GET, name = "校验密码")
    public Result<String> checkPassword(HttpServletRequest request, @RequestParam("password") String password) {
        if (password != null && !"".equals(password)) {
            UAUserDTO currentUser = systemService.getCurrentUser(request);
            if (BCrypt.checkpw(password, currentUser.getPassword())) {
                return Result.success("密码验证通过");
            } else {
                return Result.failMsg("旧密码输入不正确");
            }
        }
        return Result.failMsg("请输入旧密码");
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET, name = "删除用户")
    public Result<Integer> delete(@PathVariable Integer id) {
        return pfUserService.delete(id);
    }

}
