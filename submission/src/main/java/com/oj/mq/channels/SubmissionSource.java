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


    // 开发测试组
    String SubmissionOutput = "SubmissionOutput";
    @Output(SubmissionOutput)
    MessageChannel submissionOutput();

}
