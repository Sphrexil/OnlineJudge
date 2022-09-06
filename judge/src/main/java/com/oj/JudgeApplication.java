package com.oj;


import com.oj.mq.channels.JudgeSink;
import com.oj.mq.channels.JudgeSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.stream.annotation.EnableBinding;



@EnableBinding(value = {JudgeSource.class, JudgeSink.class})
@EnableCaching
@SpringBootApplication
public class JudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(JudgeApplication.class, args);
    }
}
