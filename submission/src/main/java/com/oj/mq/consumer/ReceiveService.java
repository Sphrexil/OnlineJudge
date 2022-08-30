package com.oj.mq.consumer;

import com.oj.mq.channels.SubmissionSink;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * description: ReceiveService
 * date: 2022/8/30 14:10
 * author: zhenyu
 * version: 1.0
 */
@Service
public class ReceiveService {


    @StreamListener(value = SubmissionSink.FirthSubmissionInput)
    public void receive1(String msg) {
        System.out.println("接收到的消息1为:"+msg);
    }
    @StreamListener(value = SubmissionSink.SecondSubmissionInput)
    public void receive2(String msg) {
        System.out.println("接收到的消息2为:"+msg);
    }
    @StreamListener(value = Sink.INPUT)
    public void receiveTest(String msg) {
        System.out.println("接收到的消息TEST为:"+msg);
    }
}
