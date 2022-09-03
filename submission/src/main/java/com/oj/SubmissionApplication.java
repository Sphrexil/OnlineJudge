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
@EnableFeignClients(basePackages = "com.oj.feign")
public class SubmissionApplication {


    public static void main(String[] args) {
        SpringApplication.run(SubmissionApplication.class, args);
    }


}
