package com.rngay.service_authority.controller;

import com.rngay.common.util.BinaryUtil;
import com.rngay.common.util.UploadUtil;
import com.rngay.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "file")
public class FileController {

    @Autowired
    private UploadUtil uploadUtil;

    @RequestMapping(value = "upload")
    public Result<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
//        String path = "D:/ftpserver/";
//        try {
//            String filename = file.getOriginalFilename();
//            String fileName = path + BinaryUtil.encodeMD5(input2byte(file.getInputStream()));
//            String imgType = filename.substring(filename.lastIndexOf("."));
//            File file1 = new File(fileName + imgType);
//            if (!file1.getParentFile().exists()) {
//                file1.getParentFile().mkdir();
//            }
//            file.transferTo(file1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String upload = uploadUtil.upload(file);
        return Result.success(upload);
    }

    private byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

}
