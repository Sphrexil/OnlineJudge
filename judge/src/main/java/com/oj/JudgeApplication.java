package com.oj;


import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;



@EnableBinding(value = {SubmissionSource.class, SubmissionSink.class})
@EnableCaching
@SpringBootApplication
public class JudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(JudgeApplication.class, args);
    }
}
