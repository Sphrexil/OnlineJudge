package com.oj;

import com.oj.mq.test.subTestService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@SpringBootTest(classes = SubmissionApplicationTests.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SubmissionApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(TestApplicationTests.class);

    private final subTestService senderService;
    SubmissionApplicationTests(subTestService service){
        this.senderService = service;
    }
    @Test
    void contextLoads() throws Exception {
        send("我是你爹");
    }

    private void send(String msg) {
        log.info(senderService.send(msg).toString());
    }
}
