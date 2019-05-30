package com.rngay.common.util;

import com.rngay.common.config.RnGayOSSConfig;
import com.rngay.common.exception.BaseException;
import com.rngay.common.vo.Result;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class UploadUtil {

    @Autowired
    private RnGayOSSConfig ossConfig;

    /**
    * ftp 文件上传
    * @Author pengcheng
    * @Date 2019/5/30
    **/
    public String ftpUpload(MultipartFile uploadFile) {
        if (uploadFile.getSize() > 10 * 1024 * 1024) {
            throw new BaseException(Result.CODE_FAIL, "文件大小不能超过10M");
        }
        String type = uploadFile.getContentType();
        String filename = uploadFile.getOriginalFilename();
        if (type == null || filename == null || filename.lastIndexOf(".") == -1) {
            throw new BaseException(Result.CODE_FAIL, "文件格式不正确");
        }

        if (!"image/jpeg".equals(type) && !"image/png".equals(type)) {
            throw new BaseException(Result.CODE_FAIL, "文件格式不正确");
        }

        FTPClient ftpClient = new FTPClient();
        InputStream inputStream = null;
        try {
            ftpClient.connect(ossConfig.getAddress(), ossConfig.getPort());
            boolean login = ftpClient.login(ossConfig.getUserName(), ossConfig.getPassword());
            if (!login) {
                throw new BaseException(Result.CODE_FAIL, "图片服务器验证连接失败");
            }
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                throw new BaseException(Result.CODE_FAIL, "图片服务器连接发生异常");
            }
            // 切换到上传目录
            if (!ftpClient.changeWorkingDirectory(ossConfig.getBasePath())) {
                // 如果目录不存在创建目录
                if (!ftpClient.makeDirectory(ossConfig.getBasePath())) {
                    throw new BaseException(Result.CODE_FAIL, "图片服务器连接发生异常");
                } else {
                    ftpClient.changeWorkingDirectory(ossConfig.getBasePath());
                }
            }
            boolean fileType = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (fileType) {
                ftpClient.enterLocalPassiveMode();
                inputStream = uploadFile.getInputStream();
                String imgType = filename.substring(filename.lastIndexOf("."));
                String md5 = BinaryUtil.encodeMD5(input2byte(inputStream));
                String fileName = md5 + imgType;
                if (!ftpClient.storeFile(fileName, inputStream)) {
                    throw new BaseException(Result.CODE_FAIL, "图片上传服务器失败");
                }
                ftpClient.logout();
                return ossConfig.getBaseUrl() + md5 + imgType;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
    * springboot 文件上传
    * @Author pengcheng
    * @Date 2019/5/30
    **/
    public String upload(MultipartFile uploadFile) {
        if (uploadFile.getSize() > 10 * 1024 * 1024) {
            throw new BaseException(Result.CODE_FAIL, "文件大小不能超过10M");
        }
        String type = uploadFile.getContentType();
        String filename = uploadFile.getOriginalFilename();
        if (type == null || filename == null || filename.lastIndexOf(".") == -1) {
            throw new BaseException(Result.CODE_FAIL, "文件格式不正确");
        }

        if (!"image/jpeg".equals(type) && !"image/png".equals(type)) {
            throw new BaseException(Result.CODE_FAIL, "文件格式不正确");
        }

        try {
            String md5 = BinaryUtil.encodeMD5(input2byte(uploadFile.getInputStream()));
            String imgType = filename.substring(filename.lastIndexOf("."));
            File file = new File(ossConfig.getBasePath() + md5 + imgType);
            if (!file.getParentFile().exists()) {
                boolean mkdir = file.getParentFile().mkdir();
                if (!mkdir) {
                    throw new BaseException(1, "图片上传失败");
                }
            }
            uploadFile.transferTo(file);
            return ossConfig.getBaseUrl() + md5 + imgType;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
