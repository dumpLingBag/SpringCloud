package com.rngay.service_authority.controller;

import com.rngay.common.util.UploadUtil;
import com.rngay.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "file")
public class FileController {

    @Autowired
    private UploadUtil uploadUtil;

    @RequestMapping(value = "upload")
    public Result<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String upload = uploadUtil.upload(file);
        return Result.success(upload);
    }

}
