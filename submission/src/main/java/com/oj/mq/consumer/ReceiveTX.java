package com.oj.mq.consumer;

import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;

/**
 * description: ReciveTX
 * date: 2022/8/31 18:27
 * author: zhenyu
 * version: 1.0
 */

@EnableBinding(SubmissionSink.class)
public class ReceiveTX {


    //消费者端接收到的消息
    @StreamListener(SubmissionSink.INPUTTX)
    public void receiveTransactionMessage(String receiveMsg) {
        System.out.println("Transaction_input 接收到的消息: " + receiveMsg);
    }
}
