package com.oj;

import com.oj.mq.test.JudgeTestService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@SpringBootTest(classes = SubmissionApplicationTests.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SubmissionApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(TestApplicationTests.class);

    private final JudgeTestService senderService;
    SubmissionApplicationTests(JudgeTestService service){
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
