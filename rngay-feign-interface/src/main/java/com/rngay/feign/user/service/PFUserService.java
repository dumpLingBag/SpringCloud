package com.rngay.feign.user.service;

import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UASaveUserDTO;
import com.rngay.feign.user.dto.UAUpdateUserDTO;
import com.rngay.feign.user.dto.UAUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "service-user")
public interface PFUserService {

    /**
    * 通过账号密码查询用户信息
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    @RequestMapping(value = "/user/find", method = RequestMethod.POST)
    Result<Map<String, Object>> find(@RequestParam String account, @RequestParam String password);

    /**
    * 通过id查询用户信息
    * @Author: pengcheng
    * @Date: 2019/1/9
    */
    @RequestMapping(value = "/user/findById/{id}", method = RequestMethod.GET)
    Result<Map<String, Object>> findById(@PathVariable Integer id);

    /**
    * 添加一个用户
    * @Author: pengcheng
    * @Date: 2018/12/16
    */
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    Result<Integer> save(UASaveUserDTO saveUserDTO);

    /**
    * 修改用户信息
    * @Author: pengcheng
    * @Date: 2018/12/28
    */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    Result<Integer> update(UAUpdateUserDTO updateUserDTO);

    /**
    * 加载 icon 图标
    * @Author: pengcheng
    * @Date: 2018/12/22
    */
    @RequestMapping(value = "/user/icon", method = RequestMethod.POST)
    Result<List<Map<String, Object>>> loadIcon();

    /**
    * 分页加载用户信息
    * @Author: pengcheng
    * @Date: 2018/12/27
    */
    @RequestMapping(value = "/user/pageList", method = RequestMethod.POST)
    Result<PageList<Map<String, Object>>> pageList(UAUserPageListDTO pageListDTO);

    /**
    * 重置用户密码
    * @Author: pengcheng
    * @Date: 2018/12/29
    */
    @RequestMapping(value = "/user/reset/{id}")
    Result<Integer> reset(@PathVariable Integer id);

    /**
    * 启禁用账号
    * @Author: pengcheng
    * @Date: 2019/1/7
    */
    @RequestMapping(value = "/user/enable/{id}/{enable}")
    Result<Integer> enable(@PathVariable Integer id, @PathVariable Integer enable);

    /**
    * 修改用户密码
    * @Author: pengcheng
    * @Date: 2019/1/14
    */
    @RequestMapping(value = "/user/updatePassword")
    Result<Integer> updatePassword(@RequestBody UpdatePassword password);

}
