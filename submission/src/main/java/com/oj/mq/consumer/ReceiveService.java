package com.oj.mq.consumer;

import com.oj.entity.SubmissionEntity;
import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;
import com.oj.mq.extractor.MyPartitionKeyExtractor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
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

//    @Override
//    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
//        Object o1 = message.getHeaders().get(MyPartitionKeyExtractor.PARTITION_PROP);
//        if ("2".equals(o1.toString())) return RocketMQLocalTransactionState.UNKNOWN;
//
//        return RocketMQLocalTransactionState.ROLLBACK;
//    }

//    @Override
//    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
//        System.out.println("消息到了这里来了"+message.getPayload());
//        return RocketMQLocalTransactionState.COMMIT;
//    }

//    @StreamListener(value = Sink.INPUT)
//    public void receiveTest(String msg) {
//        System.out.println("接收到的消息TEST为:"+msg);
//    }

}
