package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuUrlDTO;
import com.rngay.feign.authority.UpdateUrlDTO;
import com.rngay.feign.authority.UrlDTO;
import com.rngay.service_authority.service.MenuUrlService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "menuUrl")
public class MenuUrlController {

    @Autowired
    private MenuUrlService urlService;
    @Autowired
    private SystemService systemService;

    @GetMapping(value = "list")
    public Result<List<UrlDTO>> list(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId == 0) {
            return Result.success(urlService.load());
        }
        return Result.success();
    }

    @PostMapping(value = "listUrl")
    public Result<List<MenuUrlDTO>> listUrl(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        List<MenuUrlDTO> menuUrls = urlService.listUrl(updateUrlDTO.getMenuId());
        return Result.success(menuUrls);
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        return Result.success(urlService.update(updateUrlDTO));
    }

}
