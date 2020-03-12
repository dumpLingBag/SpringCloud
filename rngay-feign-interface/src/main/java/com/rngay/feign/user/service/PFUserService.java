package com.rngay.feign.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.*;
import com.rngay.feign.user.fallback.PFUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "service-user", fallbackFactory = PFUserServiceFallback.class)
public interface PFUserService {

    /**
     * 通过账号密码查询用户信息
     *
     * @Author: pengcheng
     * @Date: 2018/12/13
     */
    @GetMapping(value = "/user/find")
    Result<UaUserDTO> find(@RequestParam("username") String username, @RequestParam("password") String password);

    /**
     * 通过id查询用户信息
     *
     * @Author: pengcheng
     * @Date: 2019/1/9
     */
    @GetMapping(value = "/user/findById/{id}")
    Result<UaUserDTO> findById(@PathVariable Long id);

    /**
     * 通过账号查询信息
     *
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    @GetMapping(value = "/user/findByAccount")
    Result<UaUserDTO> findByAccount(@RequestParam("username") String username);

    /**
     * 通过手机号查询信息
     *
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    @GetMapping(value = "/user/findByMobile")
    Result<UaUserDTO> findByMobile(@RequestParam("mobile") String mobile);

    /**
     * 添加一个用户
     *
     * @Author: pengcheng
     * @Date: 2018/12/16
     */
    @PostMapping(value = "/user/save")
    Result<Integer> save(@RequestBody UaUserDTO saveUserDTO);

    /**
     * 修改用户信息
     *
     * @Author: pengcheng
     * @Date: 2018/12/28
     */
    @PutMapping(value = "/user/update")
    Result<Integer> update(@RequestBody UaUserDTO updateUserDTO);

    /**
     * 加载 icon 图标
     *
     * @Author: pengcheng
     * @Date: 2018/12/22
     */
    @GetMapping(value = "/user/icon")
    Result<List<UaIconDTO>> loadIcon();

    /**
     * 分页加载用户信息
     *
     * @Author: pengcheng
     * @Date: 2018/12/27
     */
    @PostMapping(value = "/user/pageList")
    Result<Page<UaUserDTO>> pageList(@RequestBody UaUserPageListDTO pageListDTO);

    /**
     * 重置用户密码
     *
     * @Author: pengcheng
     * @Date: 2018/12/29
     */
    @PutMapping(value = "/user/reset/{id}")
    Result<String> reset(@PathVariable Long id);

    /**
     * 启禁用账号
     *
     * @Author: pengcheng
     * @Date: 2019/1/7
     */
    @PutMapping(value = "/user/enable/{id}/{enable}")
    Result<Integer> enable(@PathVariable Long id, @PathVariable Integer enable);

    /**
     * 修改用户密码
     *
     * @Author: pengcheng
     * @Date: 2019/1/14
     */
    @PutMapping(value = "/user/updatePassword")
    Result<Integer> updatePassword(@RequestBody UpdatePassword password);

    /**
     * 通过id删除用户
     *
     * @Author: pengcheng
     * @Date: 2019/2/3
     */
    @DeleteMapping(value = "/user/delete/{id}")
    Result<Integer> delete(@PathVariable Long id);

    /**
     * 更新用户头像
     * @author pengcheng
     * @date 2020-03-07 17:20
     */
    @PostMapping(value = "/user/uploadAvatar")
    Result<Integer> uploadAvatar(@RequestParam("path") String path, @RequestParam("userId") Long userId);

}
