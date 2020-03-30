package com.rngay.service_authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.util.StringUtils;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.DictTypeDTO;
import com.rngay.feign.authority.query.DictTypeQuery;
import com.rngay.service_authority.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "dictType")
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;

    @GetMapping(value = "list")
    public Result<List<DictTypeDTO>> list() {
        return Result.success(dictTypeService.list());
    }

    @GetMapping(value = "page")
    public Result<Page<DictTypeDTO>> page(DictTypeQuery dictTypeQuery) {
        return Result.success(dictTypeService.page(dictTypeQuery));
    }

    @PostMapping(value = "insert")
    public Result<Boolean> insert(@Valid @RequestBody DictTypeDTO dictTypeDTO) {
        DictTypeDTO dictType = dictTypeService.getDictType(dictTypeDTO.getDictType());
        if (dictType != null) {
            return Result.fail("存在相同字典类型");
        }
        return Result.success(dictTypeService.save(dictTypeDTO));
    }

    @PutMapping(value = "update")
    public Result<Boolean> update(@Valid @RequestBody DictTypeDTO dictTypeDTO) {
        return Result.success(dictTypeService.updateById(dictTypeDTO));
    }

    @DeleteMapping(value = "delete/{dictTypeId}")
    public Result<Boolean> delete(@PathVariable Long dictTypeId) {
        return Result.success(dictTypeService.deleteDictType(dictTypeId));
    }

    @GetMapping(value = "getDictType/{dictType}")
    public Result<DictTypeDTO> getDictType(@PathVariable String dictType) {
        if (StringUtils.isNotBlank(dictType)) {
            return Result.success(dictTypeService.getDictType(dictType));
        }
        return Result.failMsg("查询失败");
    }

}
