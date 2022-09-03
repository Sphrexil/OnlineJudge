package com.oj.mq.consumer;

import com.oj.mq.channels.SubmissionSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;


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
