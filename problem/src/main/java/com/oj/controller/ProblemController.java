package com.oj.controller;

import java.util.Arrays;
import java.util.Map;


import com.oj.entity.ProblemEntity;
import com.oj.service.ProblemService;
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
 * @date 2022-08-27 16:35:57
 */
@RestController
@RequestMapping("generator/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

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
        ProblemEntity problemEntity = problemService.getById(id);
        return ResponseResult.okResult(problemEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public ResponseResult save(@RequestBody ProblemEntity problem){
		problemService.save(problem);

        return ResponseResult.okResult();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public ResponseResult update(@RequestBody ProblemEntity problem){
		problemService.updateById(problem);

        return ResponseResult.okResult();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseResult delete(@RequestBody String[] ids){
		problemService.removeByIds(Arrays.asList(ids));

        return ResponseResult.okResult();
    }

}
