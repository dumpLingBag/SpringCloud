package com.rngay.service_authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.aspect.annotation.Log;
import com.rngay.common.aspect.annotation.RepeatSubmit;
import com.rngay.common.aspect.enums.BusinessType;
import com.rngay.common.util.ResMsg;
import com.rngay.common.util.StringUtils;
import com.rngay.common.util.UploadUtil;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.vo.UserInfoVo;
import com.rngay.feign.user.dto.*;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private PFUserService pfUserService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private UploadUtil uploadUtil;

    @RepeatSubmit
    @PostMapping(value = "insert")
    public Result<Integer> save(@Valid @RequestBody UaUserDTO saveUserDTO) {
        Result<UaUserDTO> byAccount = pfUserService.getUserByUsername(saveUserDTO.getUsername());
        if (byAccount.getCode() == 0) {
            if (byAccount.getData() != null) {
                ResMsg.builder("username", "此账号名称已经存在");
            }
        } else {
            return Result.fail(byAccount.getMsg());
        }
        Result<UaUserDTO> byMobile = pfUserService.getUserByMobile(saveUserDTO.getMobile());
        if (byMobile.getCode() == 0) {
            if (byMobile.getData() != null) {
                ResMsg.builder("mobile", "此手机号码已经存在");
            }
        } else {
            return Result.fail(byMobile.getMsg());
        }
        if (ResMsg.getLength()) {
            return Result.fail(ResMsg.getBuilder());
        }
        saveUserDTO.setParentId(systemService.getCurrentUserId());
        return pfUserService.insert(saveUserDTO);
    }

    @PostMapping(value = "page")
    public Result<Page<UaUserDTO>> page(@RequestBody UaUserPageListDTO pageListDTO) {
        return pfUserService.page(pageListDTO);
    }

    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping(value = "update")
    public Result<Integer> update(@Valid @RequestBody UaUserDTO updateUserDTO) {
        UaUserDTO user = pfUserService.getUserById(updateUserDTO.getId()).getData();
        if (user != null) {
            if (!user.getUsername().equals(updateUserDTO.getUsername())) {
                Result<UaUserDTO> byAccount = pfUserService.getUserByUsername(updateUserDTO.getUsername());
                if (byAccount.getCode() != 0 || byAccount.getData() != null) {
                    ResMsg.builder("username", "此账号名称已经存在");
                }
            }
            if (user.getMobile() != null && !user.getMobile().equals(updateUserDTO.getMobile())) {
                Result<UaUserDTO> byMobile = pfUserService.getUserByMobile(updateUserDTO.getMobile());
                if (byMobile.getCode() != 0 || byMobile.getData() != null) {
                    ResMsg.builder("mobile", "此手机号码已经存在");
                }
            }
            if (ResMsg.getLength()) {
                return Result.fail(ResMsg.getBuilder());
            }
        } else {
            return Result.failMsg("不存在该用户");
        }
        return pfUserService.update(updateUserDTO);
    }

    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "reset/{id}")
    public Result<String> reset(@PathVariable Long id) {
        if (id == null) {
            return Result.failMsg("密码重置失败");
        }
        return pfUserService.reset(id);
    }

    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "enabled/{id}/{enabled}")
    public Result<Integer> enabled(@PathVariable Long id, @PathVariable Integer enabled) {
        UaUserDTO currentUser = systemService.getCurrentUser();
        if (currentUser.getParentId() == 0 && currentUser.getId().equals(id)) {
            return Result.failMsg("禁止修改超级用户状态");
        }
        if (id != null && enabled != null) {
            return pfUserService.enabled(id, enabled);
        } else {
            return Result.failMsg("操作失败，请联系管理员");
        }
    }

    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "updatePassword")
    public Result<Integer> updatePassword(@RequestBody UpdatePassword password) {
        Long userId = systemService.getCurrentUserId();
        UaUserDTO user = pfUserService.getUserById(userId).getData();
        if (user == null) {
            return Result.failMsg("不存在该用户，修改失败");
        }
        if (!BCrypt.checkpw(password.getOldPassword(), user.getPassword())) {
            return Result.failMsg("旧密码输入不正确");
        }
        password.setUserId(userId);
        return pfUserService.updatePassword(password);
    }

    @GetMapping(value = "checkPassword")
    public Result<String> checkPassword(@RequestParam("password") String password) {
        if (password != null && !"".equals(password)) {
            UaUserDTO currentUser = systemService.getCurrentUser();
            if (BCrypt.checkpw(password, currentUser.getPassword())) {
                return Result.success("密码验证通过");
            } else {
                return Result.failMsg("旧密码输入不正确");
            }
        }
        return Result.failMsg("请输入旧密码");
    }

    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "delete/{id}")
    public Result<Integer> delete(@PathVariable Long id) {
        UaUserDTO user = pfUserService.getUserById(id).getData();
        if (user != null) {
            if (user.getParentId() == 0) {
                return Result.failMsg("不能删除超级用户");
            }
            user.setDelFlag(0);
        }
        return pfUserService.update(user);
    }

    @PostMapping(value = "uploadAvatar")
    public Result<String> uploadAvatar(@RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile file) {
        String path = uploadUtil.ossUpload(fileName, file);
        if (StringUtils.isNoneBlank(path)) {
            Long userId = systemService.getCurrentUserId();
            pfUserService.uploadAvatar(path, userId);
            return Result.success(path);
        }
        return Result.failMsg("上传失败。");
    }

    @GetMapping(value = "getUserInfo")
    public Result<UserInfoVo> getUserInfo() {
        Long userId = systemService.getCurrentUserId();
        Result<UaUserDTO> user = pfUserService.getUserById(userId);
        UserInfoVo infoVo = new UserInfoVo();
        infoVo.setUaUserDTO(user.getData());

        return Result.success(infoVo);
    }

}
