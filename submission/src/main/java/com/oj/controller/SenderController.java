package com.oj.controller;

import com.oj.entity.SubmissionEntity;
import com.oj.mq.channels.SubmissionSource;
import com.oj.mq.producer.SenderService;
import com.oj.mq.transaction.SendTx;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: SenderController
 * date: 2022/8/31 15:08
 * author: zhenyu
 * version: 1.0
 */
@Controller
public class SenderController {
    @Autowired
    SendTx service;
    @Autowired
    SenderService senderService;

    @RequestMapping("/sendTx")
    public void sendTx() throws Exception {
        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setDelFlag(0);
        submissionEntity.setAuthor(5555L);
        service.sendTransactionalMsg(submissionEntity, 1);
        service.sendTransactionalMsg(submissionEntity, 2);
        service.sendTransactionalMsg(submissionEntity, 3);
        service.sendTransactionalMsg(submissionEntity, 4);
    }

    @RequestMapping("/send")
    @ResponseBody
    public ResponseResult send() throws Exception {
        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setDelFlag(0);
        submissionEntity.setAuthor(5555L);

        return senderService.send(submissionEntity);
    }
}
