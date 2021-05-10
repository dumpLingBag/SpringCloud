package com.rngay.feign.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.user.dto.*;
import com.rngay.feign.user.fallback.PFUserServiceFallback;
import com.rngay.feign.user.query.PageUserQuery;
import com.rngay.feign.user.query.UserQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "service-user", fallbackFactory = PFUserServiceFallback.class)
public interface PFUserService {

    @PostMapping(value = "/user/list")
    Result<List<UaUserDTO>> list(@RequestBody UserQuery userQuery);

    /**
     * 通过账号密码查询用户信息
     *
     * @Author: pengcheng
     * @Date: 2018/12/13
     */
    @GetMapping(value = "/user/getUser")
    Result<UaUserDTO> getUser(@RequestParam("username") String username, @RequestParam("password") String password);

    /**
     * 通过id查询用户信息
     *
     * @Author: pengcheng
     * @Date: 2019/1/9
     */
    @GetMapping(value = "/user/getUserById/{id}")
    Result<UaUserDTO> getUserById(@PathVariable Long id);

    /**
     * 通过账号查询信息
     *
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    @GetMapping(value = "/user/getUserByUsername")
    Result<UaUserDTO> getUserByUsername(@RequestParam("username") String username);

    /**
     * 通过手机号查询信息
     *
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    @GetMapping(value = "/user/getUserByMobile")
    Result<UaUserDTO> getUserByMobile(@RequestParam("mobile") String mobile);

    /**
     * 添加一个用户
     *
     * @Author: pengcheng
     * @Date: 2018/12/16
     */
    @PostMapping(value = "/user/insert")
    Result<Integer> insert(@RequestBody UaUserDTO saveUserDTO);

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
    Result<List<UaIconDTO>> listIcon();

    /**
     * 分页加载用户信息
     *
     * @Author: pengcheng
     * @Date: 2018/12/27
     */
    @PostMapping(value = "/user/page")
    Result<Page<UaUserDTO>> page(@RequestBody PageUserQuery userQuery);

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
    @PutMapping(value = "/user/enabled/{id}/{enabled}")
    Result<Integer> enabled(@PathVariable Long id, @PathVariable Integer enabled);

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

    /**
     * 根据 userIds 加载指定用户
     * @Author: pengcheng
     * @Date: 2020/3/13
     */
    @PostMapping(value = "/user/loadByUserIds")
    Result<List<UaUserDTO>> loadByUserIds(@RequestBody List<UserRoleDTO> roleDTO);

    /**
     * 通过邮件查找用户
     * @author pengcheng
     * @date 2020-04-26 21:34
     */
    @GetMapping(value = "/user/getUserByEmail")
    Result<UaUserDTO> getUserByEmail(@RequestParam("email") String email);

}
