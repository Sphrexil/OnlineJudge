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

    // 环境测试组
//    String FirthSubmissionOutput = "FirthSubmissionOutput";
//    String SecondSubmissionOutput = "SecondSubmissionOutput";
//
//    @Output(FirthSubmissionOutput)
//    MessageChannel firthOutput();
//    @Output(SecondSubmissionOutput)
//    MessageChannel secondOutput();
    // 开发测试组
    String SubmissionOutput = "SubmissionOutput";
    @Output(SubmissionOutput)
    MessageChannel submissionOutput();

    String ResOut = "ResOutput";
    @Output(ResOut)
    MessageChannel resOut();
}
