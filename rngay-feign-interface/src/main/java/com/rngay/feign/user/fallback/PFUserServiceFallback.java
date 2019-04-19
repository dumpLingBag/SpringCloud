package com.rngay.feign.user.fallback;

import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
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
            public Result<UAUserDTO> find(String account, String password) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UAUserDTO> findById(Integer id) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UAUserDTO> findByAccount(String account) {
                return Result.failMsg(result);
            }

            @Override
            public Result<UAUserDTO> findByMobile(String mobile) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> save(UASaveUserDTO saveUserDTO) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> update(UAUpdateUserDTO updateUserDTO) {
                return Result.failMsg(result);
            }

            @Override
            public Result<List<UAIconDTO>> loadIcon() {
                return Result.failMsg(result);
            }

            @Override
            public Result<PageList<UAUserDTO>> pageList(UAUserPageListDTO pageListDTO) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> reset(Integer id) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> enable(Integer id, Integer enable) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> updatePassword(UpdatePassword password) {
                return Result.failMsg(result);
            }

            @Override
            public Result<Integer> delete(Integer id) {
                return Result.failMsg(result);
            }

            @Override
            public Result<List<UAUserDTO>> noticeUserList(List<String> noticeUserList) {
                return Result.failMsg(result);
            }
        };
    }
}