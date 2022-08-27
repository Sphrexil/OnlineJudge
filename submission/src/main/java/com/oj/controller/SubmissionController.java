package com.oj.controller;

import java.util.Arrays;
import java.util.Map;

import com.oj.entity.SubmissionEntity;
import com.oj.service.SubmissionService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-08-27 16:37:04
 */
@RestController
@RequestMapping("generator/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public ResponseResult list(@RequestParam Map<String, Object> params){


        return ResponseResult.okResult();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public ResponseResult info(@PathVariable("id") String id){
		SubmissionEntity submission = submissionService.getById(id);

        return ResponseResult.okResult(submission);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public ResponseResult save(@RequestBody SubmissionEntity submission){
		submissionService.save(submission);

        return ResponseResult.okResult();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public ResponseResult update(@RequestBody SubmissionEntity submission){
		submissionService.updateById(submission);

        return ResponseResult.okResult();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseResult delete(@RequestBody String[] ids){
		submissionService.removeByIds(Arrays.asList(ids));

        return ResponseResult.okResult();
    }

}
