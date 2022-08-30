package com.oj.controller;

import java.util.Arrays;

import com.oj.entity.ProblemEntity;
import com.oj.service.ProblemService;
import com.oj.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/problem")
@Api("Problem相关接口")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "problem列表", notes = "不区分难度,按照时间倒序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required=true, dataType="int"),
            @ApiImplicitParam(name = "pageSize", value = "页码大小", required=true, dataType="int")
    })
    public ResponseResult list(Integer pageNum,Integer pageSize){

        return problemService.getProblemList(pageNum, pageSize);
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
