package com.rngay.authority.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.authority.service.DictDataService;
import com.rngay.common.util.MessageUtils;
import com.rngay.common.util.UploadUtil;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.DictDataDTO;
import com.rngay.feign.authority.UserFileDTO;
import com.rngay.authority.dao.UserFileDao;
import com.rngay.authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件请求类
 * @Author: pengcheng
 * @Date: 2020/4/15
 */
@RestController
@RequestMapping(value = "file")
public class FileController {

    @Autowired
    private UploadUtil uploadUtil;
    @Autowired
    private SystemService systemService;
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private DictDataService dictDataService;

    @PostMapping(value = "upload")
    public Result<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String fileName = request.getParameter("fileName");
        DictDataDTO one = dictDataService.getOne(new QueryWrapper<DictDataDTO>().eq("dict_type", "sys_upload_size"));
        String path = uploadUtil.ossUpload(fileName, file, Integer.parseInt(one.getDictValue()));
        if (path != null) {
            String subStr = path.substring(path.lastIndexOf("/") + 1);
            subStr = subStr.substring(0, subStr.lastIndexOf("."));

            int i = saveFile(file, subStr, path, request);
            if (i > 0) {
                return Result.success(path);
            }
            return Result.fail(MessageUtils.message("upload.file.fail"));
        }
        return Result.fail(MessageUtils.message("upload.file.fail"));
    }

    private int saveFile(MultipartFile file, String subStr, String path, HttpServletRequest request) {
        UserFileDTO userFile = new UserFileDTO();
        userFile.setFileId(subStr);
        userFile.setContentType(file.getContentType());
        userFile.setOriginalFilename(file.getOriginalFilename());
        userFile.setUrl(path);
        userFile.setUserId(systemService.getCurrentUserId());
        return userFileDao.insert(userFile);
    }

}
