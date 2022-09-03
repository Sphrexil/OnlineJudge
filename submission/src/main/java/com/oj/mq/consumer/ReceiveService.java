package com.oj.mq.consumer;


import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * description: ReceiveService
 * date: 2022/8/30 14:10
 * author: zhenyu
 * version: 1.0
 */
@Service
//@RocketMQTransactionListener(txProducerGroup = "tx-group", corePoolSize = 5, maximumPoolSize = 10)
public class ReceiveService {

    @Autowired
    private SubmissionSource source;

    @StreamListener(value = SubmissionSink.FirthSubmissionInput)
    public void receive1(String msg) {
        System.out.println("接收到的消息1为:"+msg);
        MessageBuilder<Object> builder = MessageBuilder.withPayload("消息处理完成");
        source.secondOutput().send(builder.build());
    }
}
