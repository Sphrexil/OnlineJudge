package com.oj.mq.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface SubmissionSink {

    String FirthSubmissionInput = "FirthSubmissionInput";
    String SecondSubmissionInput = "SecondSubmissionInput";

    @Input(FirthSubmissionInput)
    MessageChannel firthInput();
    @Input(SecondSubmissionInput)
    MessageChannel secondInput();
}
