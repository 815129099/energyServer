package com.example.demo;

import com.example.demo.util.Message.SendEmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class sendMail {

    @Autowired
    private SendEmailUtil sendEmailUtil;

    @Test
    public void testMail(){
            sendEmailUtil.sendMail("test","hhhhhhh","liu.wenxiang@cxtc.com");
    }
}
