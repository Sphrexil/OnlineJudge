package com.oj.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.oj.pojo.VO.CreateProblemVO;
import com.oj.entity.ProblemEntity;
import com.oj.entity.TestCaseEntity;
import com.oj.service.ProblemService;
import com.oj.service.TestCaseService;
import com.oj.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/problem")
@Api("Problem相关接口")
@Slf4j
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @Autowired
    private TestCaseService testCaseService;
    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "problem列表", notes = "不区分难度,按照时间倒序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required=true, dataType="int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页码大小", required=true, dataType="int", paramType = "query")
    })
    public ResponseResult getProblemList(@NotNull Integer pageNum, @NotNull Integer pageSize, Integer difficulty, Boolean isDescByDifficulty){

        return problemService.getProblemList(pageNum, pageSize, difficulty, isDescByDifficulty);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseResult getProblemById(@PathVariable("id") @NotNull Long id){

        return problemService.getProblemById(id);
    }

    @GetMapping("/showInfo/{showId}")
    public ResponseResult getProblemByShowId(@PathVariable("showId") @NotNull Long showId){

        return problemService.getProblemByShowId(showId);
    }

    /**
     * 保存
     */
//    @PostMapping("/save")
//    public ResponseResult save(@RequestBody ProblemEntity problem){
//		problemService.save(problem);
//
//        return ResponseResult.okResult();
//    }
    @PostMapping("/save")
    public ResponseResult save(@RequestBody CreateProblemVO createProblemVO){
        ProblemEntity problem = createProblemVO.getProblem();
        problemService.save(problem);
        if (Objects.nonNull(createProblemVO.getCases())) {
            ArrayList<TestCaseEntity> cases = createProblemVO.getCases();
            log.info("cases :{}", cases);
            for (TestCaseEntity i : cases){
                i.setRelatedProblem(problem.getId());
                testCaseService.save(i);
            }
        }
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
