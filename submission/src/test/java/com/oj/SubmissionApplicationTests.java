package com.oj;

import com.oj.mq.test.SubTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@SpringBootTest(classes = SubmissionApplicationTests.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Slf4j
class SubmissionApplicationTests {

    private final SubTestService senderService;
    SubmissionApplicationTests(SubTestService service){
        this.senderService = service;
    }
    @Test
    void contextLoads() throws Exception {
        send("我是你爹");
    }

    private void send(String msg) {
//        log.info(senderService.send(msg).toString());
    }
}
