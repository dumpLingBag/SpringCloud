package com.rngay.authority.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.rngay.common.config.RnGayOSSConfig;
import com.rngay.feign.authority.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String mailSender;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RnGayOSSConfig ossConfig;

    /**
     * 文本邮件
     * @author pengcheng
     * @date 2020-03-08 18:52
     */
    public void sendSimpleMail(MailDTO mailDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailSender);
            message.setTo(mailDTO.getRecipient());
            message.setSubject(mailDTO.getSubject());
            message.setText(mailDTO.getContent());
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * html 格式邮件发送
     * @author pengcheng
     * @date 2020-03-08 18:56
     */
    public void sendHtmlMail(MailDTO mailDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailSender);
            mimeMessageHelper.setTo(mailDTO.getRecipient());
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(mailDTO.getContent(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送附件邮件
     * @author pengcheng
     * @date 2020-03-08 19:00
     */
    public void sendAttachmentMail(MailDTO mailDTO) throws IOException {
        BufferedReader reader = null;
        OSS oss = null;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailSender);
            mimeMessageHelper.setTo(mailDTO.getRecipient());
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(mailDTO.getContent());
            oss = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
            OSSObject object = oss.getObject(ossConfig.getBucketName(), mailDTO.getFileName());
            // 读取文件内容。
            reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
            FileSystemResource file = new FileSystemResource(reader.readLine());
            mimeMessageHelper.addAttachment(mailDTO.getEnclosureName(), file);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            if (reader != null) {
                reader.close();
            }
            // 关闭OSSClient。
            if (oss != null) {
                oss.shutdown();
            }
        }
    }

}
