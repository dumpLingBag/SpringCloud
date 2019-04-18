package com.rngay.feign.user.service;

import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "service-user")
public interface PFUserService {

    /**
    * 通过账号密码查询用户信息
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    @RequestMapping(value = "/user/find", method = RequestMethod.POST)
    Result<UAUserDTO> find(@RequestParam("account") String account, @RequestParam("password") String password);

    /**
    * 通过id查询用户信息
    * @Author: pengcheng
    * @Date: 2019/1/9
    */
    @RequestMapping(value = "/user/findById/{id}", method = RequestMethod.GET)
    Result<UAUserDTO> findById(@PathVariable Integer id);

    /**
     * 通过账号查询信息
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    @RequestMapping(value = "/user/findByAccount", method = RequestMethod.GET)
    Result<UAUserDTO> findByAccount(@RequestParam("account") String account);

    /**
     * 通过手机号查询信息
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    @RequestMapping(value = "/user/findByMobile", method = RequestMethod.GET)
    Result<UAUserDTO> findByMobile(@RequestParam("mobile") String mobile);

    /**
    * 添加一个用户
    * @Author: pengcheng
    * @Date: 2018/12/16
    */
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    Result<Integer> save(@RequestBody UASaveUserDTO saveUserDTO);

    /**
    * 修改用户信息
    * @Author: pengcheng
    * @Date: 2018/12/28
    */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    Result<Integer> update(@RequestBody UAUpdateUserDTO updateUserDTO);

    /**
    * 加载 icon 图标
    * @Author: pengcheng
    * @Date: 2018/12/22
    */
    @RequestMapping(value = "/user/icon", method = RequestMethod.GET)
    Result<List<UAIconDTO>> loadIcon();

    /**
    * 分页加载用户信息
    * @Author: pengcheng
    * @Date: 2018/12/27
    */
    @RequestMapping(value = "/user/pageList", method = RequestMethod.POST)
    Result<PageList<UAUserDTO>> pageList(@RequestBody UAUserPageListDTO pageListDTO);

    /**
    * 重置用户密码
    * @Author: pengcheng
    * @Date: 2018/12/29
    */
    @RequestMapping(value = "/user/reset/{id}", method = RequestMethod.GET)
    Result<Integer> reset(@PathVariable Integer id);

    /**
    * 启禁用账号
    * @Author: pengcheng
    * @Date: 2019/1/7
    */
    @RequestMapping(value = "/user/enable/{id}/{enable}", method = RequestMethod.GET)
    Result<Integer> enable(@PathVariable Integer id, @PathVariable Integer enable);

    /**
    * 修改用户密码
    * @Author: pengcheng
    * @Date: 2019/1/14
    */
    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.GET)
    Result<Integer> updatePassword(@RequestBody UpdatePassword password);
    
    /** 
    * 通过id删除用户
    * @Author: pengcheng 
    * @Date: 2019/2/3 
    */
    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    Result<Integer> delete(@PathVariable Integer id);

}
