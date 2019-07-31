package com.example.demo.util.Message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    //意思是异步调用这个方法
    public void sendMail(String title,String text,String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("815129099@qq.com"); // 发送人的邮箱
        message.setSubject(title); //标题
        message.setTo(email); //发给谁  对方邮箱
        message.setText(text); //内容
        javaMailSender.send(message); //发送
    }


}
