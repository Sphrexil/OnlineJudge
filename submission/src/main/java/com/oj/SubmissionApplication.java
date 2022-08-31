package com.oj;

import com.oj.entity.SubmissionEntity;
import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;
import com.oj.mq.extractor.MyPartitionKeyExtractor;
import com.oj.mq.producer.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.concurrent.atomic.AtomicInteger;


@EnableBinding(value = {SubmissionSource.class, SubmissionSink.class})
@SpringBootApplication
@EnableFeignClients(basePackages = "com.oj.feign")
public class SubmissionApplication {


    public static void main(String[] args) {
        SpringApplication.run(SubmissionApplication.class, args);
    }

    @Bean
    public AtomicInteger index() {
        return new AtomicInteger(0);
    }
}
