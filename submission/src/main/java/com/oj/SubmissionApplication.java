package com.oj;

import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;
import com.oj.mq.producer.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;


@EnableBinding(value = {Source.class, SubmissionSource.class, SubmissionSink.class, Sink.class})
@SpringBootApplication
public class SubmissionApplication implements CommandLineRunner {
    @Autowired
    SenderService service;

    public static void main(String[] args) {
        SpringApplication.run(SubmissionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
         service.send("hello");
    }
}
