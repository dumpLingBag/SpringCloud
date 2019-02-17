package com.rngay.service_user.controller;

import com.rngay.common.jpa.dao.impl.JdbcDao;
import com.rngay.common.util.MapUtil;
import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UASaveUserDTO;
import com.rngay.feign.user.dto.UAUpdateUserDTO;
import com.rngay.feign.user.dto.UAUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;
import com.rngay.service_user.model.UAUser;
import com.rngay.service_user.service.UAUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
public class PlatformUserController {

    @Resource
    private UAUserService uaUserService;
    @Resource
    private JdbcDao jdbcDao;

    @RequestMapping(value = "sql")
    public Result<?> sql(){
        UAUser byId = jdbcDao.findById(UAUser.class, 1);
        return Result.success(byId);
    }

    @RequestMapping(value = "find")
    public Result<Map<String, Object>> find(@RequestParam String account, @RequestParam String password){
        return Result.success(uaUserService.findUser(account, password));
    }

    @RequestMapping(value = "findById/{id}", method = RequestMethod.GET)
    public Result<Map<String, Object>> findById(@PathVariable Integer id){
        return Result.success(uaUserService.findUserById(id));
    }

    @RequestMapping(value = "findByAccount", method = RequestMethod.POST)
    public Result<?> findByAccount(@RequestParam String account){
        return Result.success(uaUserService.findByAccount(account));
    }

    @RequestMapping(value = "findByMobile", method = RequestMethod.POST)
    public Result<Map<String, Object>> findByMobile(String mobile){
        return Result.success(uaUserService.findByMobile(mobile));
    }

    @RequestMapping(value = "save")
    public Result<Integer> save(@RequestBody UASaveUserDTO saveUserDTO){
        Map<String, Object> map = MapUtil.entityToMap(saveUserDTO);
        if (map != null && !map.isEmpty()){
            map.put("create_time", new Date());
            map.put("update_time", new Date());
        }
        return Result.success(uaUserService.insertUser(map));
    }

    @RequestMapping(value = "update")
    public Result<Integer> update(@RequestBody UAUpdateUserDTO updateUserDTO){
        Map<String, Object> map = MapUtil.entityToMap(updateUserDTO);
        return Result.success(uaUserService.updateUser(map));
    }

    @RequestMapping(value = "pageList")
    public Result<PageList<Map<String, Object>>> pageList(@RequestBody UAUserPageListDTO pageListDTO){
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
