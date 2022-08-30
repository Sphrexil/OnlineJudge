package com.oj;






import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(value = Source.class)
@SpringBootTest(classes = SubmissionApplicationTests.class)
class SubmissionApplicationTests {

    @Autowired
    private Source source;



    @Test
    void contextLoads() throws Exception {
        send("我是你爹");
    }



    private void send(String msg) {
        boolean flag = source.output().send(MessageBuilder.withPayload(msg).build());
        System.out.println("消息发送"+(flag?"成功":"失败")+"了");
    }
}
