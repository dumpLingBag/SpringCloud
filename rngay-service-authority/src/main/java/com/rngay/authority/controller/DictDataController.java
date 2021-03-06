package com.rngay.authority.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.common.util.MessageUtils;
import com.rngay.common.util.StringUtils;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.DictDataDTO;
import com.rngay.feign.authority.query.DictDataQuery;
import com.rngay.authority.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "dictData")
public class DictDataController {

    @Autowired
    private DictDataService dictDataService;

    @GetMapping(value = "list")
    public Result<List<DictDataDTO>> list(DictDataQuery dictDataQuery) {
        QueryWrapper<DictDataDTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dictDataQuery.getDictType())) {
            wrapper.eq("dict_type", dictDataQuery.getDictType());
        } else {
            return Result.failMsg(MessageUtils.message("query.dict.fail"));
        }
        if (StringUtils.isNotBlank(dictDataQuery.getDictDataLabel())) {
            wrapper.eq("dict_label", dictDataQuery.getDictDataLabel());
        }
        if (StringUtils.isNotNull(dictDataQuery.getEnabled())) {
            wrapper.eq("enabled", dictDataQuery.getEnabled());
        }
        return Result.success(dictDataService.list(wrapper));
    }

    @GetMapping(value = "listDictData")
    public Result<List<DictDataDTO>> listDictData(String dictType) {
        QueryWrapper<DictDataDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type", dictType);
        wrapper.eq("del_flag", "1");
        return Result.success(dictDataService.list(wrapper));
    }

    @PostMapping(value = "insert")
    public Result<Boolean> insert(@Valid @RequestBody DictDataDTO dictDataDTO) {
        return Result.success(dictDataService.save(dictDataDTO));
    }

    @PutMapping(value = "update")
    public Result<Boolean> update(@Valid @RequestBody DictDataDTO dictDataDTO) {
        return Result.success(dictDataService.updateById(dictDataDTO));
    }

    @DeleteMapping(value = "delete/{dictDataId}")
    public Result<Boolean> delete(@PathVariable Long dictDataId) {
        DictDataDTO dictDataDTO = dictDataService.getById(dictDataId);
        if (dictDataDTO != null) {
            dictDataDTO.setDelFlag(0);
            return Result.success(dictDataService.updateById(dictDataDTO));
        }
        return Result.failMsg(MessageUtils.message("not.dict.data"));
    }

}
