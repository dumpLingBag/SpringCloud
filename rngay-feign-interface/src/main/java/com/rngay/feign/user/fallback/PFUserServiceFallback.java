package com.rngay.feign.user.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.user.dto.*;
import com.rngay.feign.user.service.PFUserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PFUserServiceFallback implements FallbackFactory<PFUserService> {
    @Override
    public PFUserService create(Throwable throwable) {
        String result = "用户服务异常，请稍后再试";
        return new PFUserService() {
            @Override
            public Result<UaUserDTO> find(String account, String password) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UaUserDTO> findById(Long id) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UaUserDTO> findByAccount(String account) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UaUserDTO> findByMobile(String mobile) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> insert(UaUserDTO saveUserDTO) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> update(UaUserDTO updateUserDTO) {
                return Result.failMsg(result);
            }

            @Override
            public Result<List<UaIconDTO>> listIcon() {
                return Result.failMsg(result);
            }

            @Override
            public Result<Page<UaUserDTO>> page(UaUserPageListDTO pageListDTO) {
                return Result.failMsg(result);
            }

            @Override
            public Result<String> reset(Long id) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> enabled(Long id, Integer enabled) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> updatePassword(UpdatePassword password) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> delete(Long id) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> uploadAvatar(String path, Long userId) {
                return Result.failMsg(result);
            }

            @Override
            public Result<List<UaUserDTO>> loadByUserIds(List<UserRoleDTO> roleDTO) {
                return Result.failMsg(result);
            }
        };
    }
}
