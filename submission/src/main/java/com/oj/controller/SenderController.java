package com.oj.controller;

import com.oj.entity.SubmissionEntity;
import com.oj.mq.test.SubTestService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * description: SenderController
 * date: 2022/8/31 15:08
 * author: zhenyu
 * version: 1.0
 */
@Controller
public class SenderController {

    @Autowired
    SubTestService senderService;

    @PostMapping("/send")
    @ResponseBody
    public ResponseResult send(@RequestBody SubmissionEntity submission) throws Exception {

        return senderService.send(submission);
    }
}
