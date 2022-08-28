package com.oj.controller;

import java.util.Arrays;
import java.util.List;


import com.oj.entity.ProblemEntity;
import com.oj.service.ProblemService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseResult list(){

        List<ProblemEntity> list = problemService.list();
        return ResponseResult.okResult(list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseResult info(@PathVariable("id") Long id){
        ProblemEntity problemEntity = problemService.getById(id);
        return ResponseResult.okResult(problemEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody ProblemEntity problem){
		problemService.save(problem);

        return ResponseResult.okResult();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseResult update(@RequestBody ProblemEntity problem){
		problemService.updateById(problem);

        return ResponseResult.okResult();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestBody Long[] ids){
		problemService.removeByIds(Arrays.asList(ids));

        return ResponseResult.okResult();
    }

}
