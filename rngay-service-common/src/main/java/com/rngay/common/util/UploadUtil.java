package com.rngay.common.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.rngay.common.config.RnGayOSSConfig;
import com.rngay.common.exception.BaseException;
import com.rngay.common.vo.Result;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class UploadUtil {

    @Autowired
    private RnGayOSSConfig ossConfig;

    /**
     * oss 文件上传
     * @Author pengcheng
     * @Date 2019/5/30
     **/
    public String ossUpload(String fileName, MultipartFile uploadFile) {
        byteLength(uploadFile);
        if (StringUtils.isNoneBlank(fileName) && fileName.lastIndexOf(".") == -1) {
            return null;
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        try {
            String md5 = BinaryUtil.encodeMD5(input2byte(uploadFile.getInputStream()));
            String imgType = fileName.substring(fileName.lastIndexOf("."));
            DateTime dateTime = new DateTime();
            String filePath = "images/" + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/" +
                    dateTime.toString("dd") + "/" + md5 + imgType;
            ossClient.putObject(ossConfig.getBucketName(), filePath, new ByteArrayInputStream(uploadFile.getBytes()));
            return ossConfig.getBaseUrl() + filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * springboot 文件上传
    * @Author pengcheng
    * @Date 2019/5/30
    **/
    /*public String upload(MultipartFile uploadFile) {
        String filename = byteLength(uploadFile);

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
    }*/

    private byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    private void byteLength(MultipartFile uploadFile) {
        if (uploadFile.getSize() > 10 * 1024 * 1024) {
            throw new BaseException(Result.CODE_FAIL, "文件大小不能超过10M");
        }
        String type = uploadFile.getContentType();

        if (!"image/jpeg".equals(type) && !"image/png".equals(type)) {
            throw new BaseException(Result.CODE_FAIL, "文件格式不正确");
        }
    }

}
