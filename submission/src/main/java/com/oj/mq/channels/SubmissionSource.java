package com.oj.mq.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * description: SubmissionSource
 * date: 2022/8/30 13:47
 * author: zhenyu
 * version: 1.0
 */

public interface SubmissionSource {

    String FirthSubmissionOutput = "FirthSubmissionOutput";
    String SecondSubmissionOutput = "SecondSubmissionOutput";

    @Output(FirthSubmissionOutput)
    MessageChannel firthOutput();
    @Output(SecondSubmissionOutput)
    MessageChannel secondOutput();

    String OUTPUTTX = "outputTX";
    @Output(OUTPUTTX)
    MessageChannel outputTX();

}
