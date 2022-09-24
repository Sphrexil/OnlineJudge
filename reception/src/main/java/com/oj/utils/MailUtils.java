package com.oj.utils;



import com.oj.constant.ReceptionConstant;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

@Configuration
@Slf4j
public class MailUtils {

    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;

    @Autowired
    private RedisCache redisCache;
    private  Message message;

    public void sendMail(String email, String subject, String emailMsg)
            throws AddressException, MessagingException {
        Properties props = new Properties();
        //设置发送的协议
        props.setProperty("mail.transport.protocol", protocol);

        //设置发送邮件的服务器
        props.setProperty("mail.host", host);
        props.setProperty("mail.smtp.auth", "true");// 指定验证为true


        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //设置发送人的帐号和密码
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        message = new MimeMessage(session);

        //设置发送者
        try {
            message.setFrom(new InternetAddress(username));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //设置发送方式与接收者
        message.setRecipient(RecipientType.TO, new InternetAddress(email));

        /* 防止被当垃圾邮件 */
        message.setHeader("X-Priority", "3");
        message.setHeader("X-MSMail-Priority", "Normal");
        message.setHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
        message.setHeader("X-MimeOLE", "Produced By Microsoft MimeOLE V6.00.2900.2869");
        message.setHeader("ReturnReceipt", "1");
        /* 防止被当垃圾邮件 */

        //设置邮件主题
        message.setSubject(subject);
        // message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

        //设置邮件内容
        message.setContent(emailMsg, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);

    }
    public static String readHtmlToString(String htmlFileName) throws Exception{
        InputStream is = null;
        Reader reader = null;
        try {
            is = MailUtils.class.getClassLoader().getResourceAsStream(htmlFileName);
            if (is ==  null) {
                throw new Exception("未找到模板文件");
            }
            reader = new InputStreamReader(is, "UTF-8");
            StringBuilder sb = new StringBuilder();
            int bufferSize = 1024;
            char[] buffer = new char[bufferSize];
            int length = 0;
            while ((length = reader.read(buffer, 0, bufferSize)) != -1){
                sb.append(buffer, 0, length);
            }
            return sb.toString();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("关闭io流异常", e);
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch ( IOException e) {
                log.error("关闭io流异常", e);
            }
        }
    }

    public void checkMailCode(String userCommitCode, String mail) {
        String code = redisCache.getCacheObject(ReceptionConstant.MAIL_CODE + "::" + mail);
        if (!StringUtils.hasText(code) || !code.equals(userCommitCode)) {
            throw new SystemException(ResultCode.VALIDATE_CODE_ERROR);
        }
    }
}

