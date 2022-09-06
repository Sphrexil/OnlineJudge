package com.oj.mq.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SubmissionSink {

    String ResInput = "ResInput";
    @Input(ResInput)
    SubscribableChannel resInput();
}
