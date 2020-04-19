package com.rngay.feign.user.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.exection.RestException;
import com.rngay.feign.user.dto.*;
import com.rngay.feign.user.query.PageUserQuery;
import com.rngay.feign.user.query.UserQuery;
import com.rngay.feign.user.service.PFUserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PFUserServiceFallback implements FallbackFactory<PFUserService> {
    @Override
    public PFUserService create(Throwable throwable) {
        String result = RestException.getMessage(throwable);
        return new PFUserService() {
            @Override
            public Result<List<UaUserDTO>> list(UserQuery userQuery) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UaUserDTO> getUser(String account, String password) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UaUserDTO> getUserById(Long id) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UaUserDTO> getUserByUsername(String username) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UaUserDTO> getUserByMobile(String mobile) {
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
            public Result<Page<UaUserDTO>> page(PageUserQuery userQuery) {
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
