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
    public Result<Integer> save(@RequestBody UASaveUserDTO saveUserDTO){
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
        Map<String, Object> user = pfUserService.findById(updateUserDTO.getId()).getData();
        if (user != null && !user.isEmpty()){
            if (user.get("account").equals("admin")){
                return Result.fail("禁止修改管理员账户");
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

}
