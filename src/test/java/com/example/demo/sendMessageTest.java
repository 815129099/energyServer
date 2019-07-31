package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class sendMessageTest {
    /**
     * 随机生成6位验证码
     * @return
     */
    public static String getRandomCode(){
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++){
            result+=random.nextInt(10);
        }
        return result;
    }

    /**
     * @author：lvfang
     * @mathName： testSendMessage
     * @parameter： 无
     * @return value：
     * @throws null
     * @date 2018/8/11
     * @desc SMS短信测试
     */
    @Test
    public void testSendMessage(){
//        SendMessageUtil.send("SMS账户","接口秘钥","目标号码","发送内容");
        //SendMessageUtil.send("testGesac","d41d8cd98f00b204e980","13950005978","嘿、俊伊，这是测试短信，请勿回复，剩余免费短信条数：3");
       // System.out.println(SendMessageUtil.getMessage(resultCode));
    }
}
