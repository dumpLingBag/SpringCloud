package com.rngay.service_authority.controller;

import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.*;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "user")
public class UAUserController {

    @Resource
    private PFUserService pfUserService;
    @Resource
    private UASystemService systemService;

    @RequestMapping(value = "save")
    public Result<Integer> save(@Valid @RequestBody UASaveUserDTO saveUserDTO){
        StringBuilder builder = new StringBuilder();
        Result<UAUserDTO> byAccount = pfUserService.findByAccount(saveUserDTO.getAccount());
        if (byAccount.getData() != null){
            builder.append("account").append(":").append("此账号名称已经存在").append("_");
        }
        Result<UAUserDTO> byMobile = pfUserService.findByMobile(saveUserDTO.getMobile());
        if (byMobile.getData() != null){
            builder.append("mobile").append(":").append("此手机号码已经存在").append("_");
        }
        if (builder.length() != 0) {
            builder.setCharAt(builder.length() - 1, ' ');
            return Result.fail(builder.toString());
        }
        return pfUserService.save(saveUserDTO);
    }

    @RequestMapping(value = "pageList")
    public Result<PageList<UAUserDTO>> pageList(@RequestBody UAUserPageListDTO pageListDTO){
        return pfUserService.pageList(pageListDTO);
    }

    @RequestMapping(value = "update")
    public Result<?> update(@Valid @RequestBody UAUpdateUserDTO updateUserDTO){
        UAUserDTO user = pfUserService.findById(updateUserDTO.getId()).getData();
        if (user != null){
            if (user.getAccount().equals("admin") && !updateUserDTO.getAccount().equals("admin")){
                return Result.fail("禁止修改管理员账户名称");
            }
            StringBuilder builder = new StringBuilder();
            if (!user.getAccount().equals(updateUserDTO.getAccount())){
                Result<UAUserDTO> byAccount = pfUserService.findByAccount(updateUserDTO.getAccount());
                if (byAccount.getData() != null){
                    builder.append("account").append(":").append("此账号名称已经存在").append("_");
                }
            }
            if (!user.getMobile().equals(updateUserDTO.getMobile())){
                Result<UAUserDTO> byMobile = pfUserService.findByMobile(updateUserDTO.getMobile());
                if (byMobile.getData() != null){
                    builder.append("mobile").append(":").append("此手机号码已经存在").append("_");
                }
            }
            if (builder.length() > 0){
                builder.setCharAt(builder.length() - 1, ' ');
                return Result.fail(builder.toString());
            }
        } else {
            return Result.fail("不存在该用户");
        }
        return pfUserService.update(updateUserDTO);
    }

    @RequestMapping(value = "reset/{id}")
    public Result<Integer> reset(@PathVariable Integer id){
        if (id == null)
            return Result.fail("重置失败");
        return pfUserService.reset(id);
    }

    @RequestMapping(value = "enable/{id}/{enable}")
    public Result<Integer> enable(@PathVariable Integer id, @PathVariable Integer enable){
        if (id != null && enable != null){
            return pfUserService.enable(id, enable);
        } else {
            return Result.fail("缺少参数 { id , enable }");
        }
    }

    @RequestMapping(value = "updatePassword")
    public Result<Integer> updatePassword(HttpServletRequest request, @RequestBody UpdatePassword password){
        Integer userId = systemService.getCurrentUserId(request);
        UAUserDTO user = pfUserService.findById(userId).getData();
        if (user == null){
            return Result.fail("不存在该用户，修改失败");
        }
        if (!BCrypt.checkpw(password.getOldPassword(), user.getPassword())) {
            return Result.fail("旧密码输入不正确");
        }
        password.setUserId(userId);
        return pfUserService.updatePassword(password);
    }

    @RequestMapping(value = "checkPassword")
    public Result<String> checkPassword(HttpServletRequest request, @RequestParam("password") String password) {
        if (password != null && !"".equals(password)) {
            UAUserDTO currentUser = systemService.getCurrentUser(request);
            if (BCrypt.checkpw(password, currentUser.getPassword())) {
                return Result.success("密码验证通过");
            } else {
                return Result.fail("旧密码输入不正确");
            }
        }
        return Result.fail("请输入旧密码");
    }
    
    @RequestMapping(value = "delete/{id}")
    public Result<Integer> delete(@PathVariable Integer id){
        return pfUserService.delete(id);
    }

}
