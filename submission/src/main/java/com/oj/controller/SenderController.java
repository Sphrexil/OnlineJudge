package com.oj.controller;

import com.oj.entity.SubmissionEntity;
import com.oj.mq.test.subTestService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description: SenderController
 * date: 2022/8/31 15:08
 * author: zhenyu
 * version: 1.0
 */
@Controller
public class SenderController {

    @Autowired
    subTestService senderService;

    @RequestMapping("/send")
    @ResponseBody
    public ResponseResult send(@RequestBody SubmissionEntity submission) throws Exception {

        return senderService.send(submission);
    }
}
