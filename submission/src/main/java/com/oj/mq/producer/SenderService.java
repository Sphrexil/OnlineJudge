package com.oj.mq.producer;

import com.oj.mq.channels.SubmissionSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * description: SenderService
 * date: 2022/8/30 11:27
 * author: cxf
 * version: 1.0
 */

@Service
public class SenderService {

    @Autowired
    private Source source;

    @Autowired
    private SubmissionSource submissionSource;

    public void send(String msg) {
        boolean flagTest = source.output().send(MessageBuilder.withPayload(msg).build());

        boolean flag1 = submissionSource.firthOutput().send(MessageBuilder.withPayload(msg).build());
        boolean flag2 = submissionSource.secondOutput().send(MessageBuilder.withPayload(msg).build());
        System.out.println("消息发送"+(flagTest?"flagTest成功":"flagTest失败")+"了");
        System.out.println("消息发送"+(flag1?"flag1成功":"flag1失败")+"了");
        System.out.println("消息发送"+(flag2?"flag2成功":"flag2失败")+"了");
    }
}
