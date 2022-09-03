package com.oj.mq.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SubmissionSink {

    String FirthSubmissionInput = "FirthSubmissionInput";
    String SecondSubmissionInput = "SecondSubmissionInput";

    @Input(FirthSubmissionInput)
    SubscribableChannel firthInput();
    @Input(SecondSubmissionInput)
    SubscribableChannel secondInput();

    String INPUTTX = "inputTX";
    @Input(INPUTTX)
    SubscribableChannel inputTX();
}
