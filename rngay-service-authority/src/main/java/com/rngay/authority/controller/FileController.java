package com.rngay.authority.controller;

import com.rngay.authority.enums.ResultCodeEnum;
import com.rngay.common.util.UploadUtil;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.UserFileDTO;
import com.rngay.authority.dao.UserFileDao;
import com.rngay.authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "file")
public class FileController {

    @Autowired
    private UploadUtil uploadUtil;
    @Autowired
    private SystemService systemService;
    @Autowired
    private UserFileDao userFileDao;

    @PostMapping(value = "upload")
    public Result<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String fileName = request.getParameter("fileName");
        String path = uploadUtil.ossUpload(fileName, file);
        if (path != null) {
            String subStr = path.substring(path.lastIndexOf("/") + 1);
            subStr = subStr.substring(0, subStr.lastIndexOf("."));

            int i = saveFile(file, subStr, path, request);
            if (i > 0) {
                return Result.success(path);
            }
            return Result.fail();
        }
        return Result.fail();
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
