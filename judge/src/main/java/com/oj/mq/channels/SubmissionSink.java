package com.oj.mq.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SubmissionSink {

//    String FirthSubmissionInput = "FirthSubmissionInput";
//    String SecondSubmissionInput = "SecondSubmissionInput";
//
//    @Input(FirthSubmissionInput)
//    SubscribableChannel firthInput();
//    @Input(SecondSubmissionInput)
//    SubscribableChannel secondInput();

//    String ResInput = "ResInput";
//    @Input(ResInput)
//    SubscribableChannel resInput();

    // 开发测试组
    String SubmissionInput = "SubmissionInput";
    @Input(SubmissionInput)
    SubscribableChannel submissionInput();
}
