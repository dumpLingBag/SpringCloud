package com.rngay.service_authority.controller;

import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.util.UploadUtil;
import com.rngay.common.vo.Result;
import com.rngay.service_authority.model.UserFile;
import com.rngay.service_authority.service.UASystemService;
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
    @Autowired
    private UASystemService systemService;
    @Autowired
    private Dao dao;

    @RequestMapping(value = "upload")
    public Result<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String path = uploadUtil.upload(file);
        if (path != null) {
            String subStr = path.substring(path.lastIndexOf("/") + 1);
            subStr = subStr.substring(0, subStr.lastIndexOf("."));

            UserFile userFile = new UserFile();
            userFile.setId(subStr);
            userFile.setContentType(file.getContentType());
            userFile.setOriginalFilename(file.getOriginalFilename());
            userFile.setUrl(path);
            userFile.setUserId(systemService.getCurrentUserId(request));
            dao.insert(userFile);
            return Result.success(path);
        }
        return Result.failMsg("文件上传失败");
    }

}
