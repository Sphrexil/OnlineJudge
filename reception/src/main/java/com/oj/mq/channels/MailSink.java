package com.oj.mq.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MailSink {

    String MailInput = "MailInput";
    @Input(MailInput)
    SubscribableChannel mailInput();
}
