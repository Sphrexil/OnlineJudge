package com.oj.mq.transaction;

import com.oj.mq.channels.SubmissionSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

/**
 * description: snedTx
 * date: 2022/8/31 18:15
 * author: zhenyu
 * version: 1.0
 */
@Component
public class SendTx {

    @Autowired
    public SubmissionSource submissionSource;

    public <T> void sendTransactionalMsg(T msg ,int num) throws Exception{
        Message<T> message = MessageBuilder.withPayload(msg)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .setHeader("test", String.valueOf(num)).build();

        //.setHeader(RocketMQHeaders.TAGS,"binder");
        submissionSource.outputTX().send(message);
    }
}
