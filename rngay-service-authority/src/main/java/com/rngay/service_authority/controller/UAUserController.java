package com.rngay.service_authority.controller;

import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UASaveUserDTO;
import com.rngay.feign.user.dto.UAUpdateUserDTO;
import com.rngay.feign.user.dto.UAUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.common.util.BindingUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
public class UAUserController {

    @Resource
    private PFUserService pfUserService;

    @RequestMapping(value = "save")
    public Result<?> save(@Valid @RequestBody UASaveUserDTO saveUserDTO, BindingResult result){
        if (BindingUtils.bindingResult(result).getCode() != 0)
            return BindingUtils.bindingResult(result);
        Result<String> repeat = repeat(saveUserDTO.getAccount(), saveUserDTO.getMobile());
        if (repeat.getCode() != 0){
            return Result.fail(repeat.getMsg());
        }
        return pfUserService.save(saveUserDTO);
    }

    @RequestMapping(value = "pageList")
    public Result<PageList<Map<String, Object>>> pageList(@RequestBody UAUserPageListDTO pageListDTO){
        return pfUserService.pageList(pageListDTO);
    }

    @RequestMapping(value = "update")
    public Result<?> update(@Valid @RequestBody UAUpdateUserDTO updateUserDTO, BindingResult result){
        if (BindingUtils.bindingResult(result).getCode() != 0)
            return BindingUtils.bindingResult(result);
        Result<String> repeat = repeat(updateUserDTO.getAccount(), updateUserDTO.getMobile());
        if (repeat.getCode() != 0){
            return repeat;
        }
        Map<String, Object> user = pfUserService.findById(updateUserDTO.getId()).getData();
        if (user != null && !user.isEmpty()){
            if (user.get("account").equals("admin") && !updateUserDTO.getAccount().equals("admin")){
                return Result.fail("禁止修改管理员账户名称");
            }
            StringBuilder builder = new StringBuilder();
            if (!user.get("account").equals(updateUserDTO.getAccount())){
                Result<Map<String, Object>> byAccount = pfUserService.findByAccount(updateUserDTO.getAccount());
                if (byAccount.getData() != null && !byAccount.getData().isEmpty()){
                    builder.append("account").append(":").append("此账号名称已经存在");
                }
            }
            if (!user.get("mobile").equals(updateUserDTO.getMobile())){
                Result<Map<String, Object>> byMobile = pfUserService.findByMobile(updateUserDTO.getMobile());
                if (byMobile.getData() != null && !byMobile.getData().isEmpty()){
                    builder.append("mobile").append(":").append("此手机号码已经存在");
                }
            }
            if (builder.length() > 0){
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
    public Result<Integer> updatePassword(@RequestBody UpdatePassword password){
        Map<String, Object> user = pfUserService.findById(password.getUserId()).getData();
        if (user == null || user.isEmpty()){
            return Result.fail("不存在该用户，修改失败");
        }
        if (!user.get("password").equals(password.getOldPassword())){
            return Result.fail("旧密码输入不正确");
        }
        return pfUserService.updatePassword(password);
    }
    
    @RequestMapping(value = "delete/{id}")
    public Result<Integer> delete(@PathVariable Integer id){
        return pfUserService.delete(id);
    }

    private Result<String> repeat(String account,String mobile){
        StringBuilder builder = new StringBuilder();
        Result<Map<String, Object>> byAccount = pfUserService.findByAccount(account);
        if (byAccount.getData() != null && !byAccount.getData().isEmpty()){
            builder.append("account").append(":").append("此账号名称已经存在");
        }
        Result<Map<String, Object>> byMobile = pfUserService.findByMobile(mobile);
        if (byMobile.getData() != null && !byMobile.getData().isEmpty()){
            builder.append("mobile").append(":").append("此手机号码已经存在");
        }

        if (builder.length() > 0){
            return Result.fail(builder.toString());
        }

        return Result.success();
    }

}
