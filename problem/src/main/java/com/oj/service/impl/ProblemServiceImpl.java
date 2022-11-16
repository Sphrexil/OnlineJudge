package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oj.constants.ProblemConstant;
import com.oj.dao.ProblemDao;
import com.oj.entity.ProblemEntity;
import com.oj.service.ProblemService;
import com.oj.utils.PageUtils;
import com.oj.utils.ResponseResult;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Objects;


@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemDao, ProblemEntity> implements ProblemService {

    @Override
    public ResponseResult getProblemList(Integer pageNum, Integer pageSize, Integer difficulty, Boolean isDescByDifficulty) {

        // 查询
        LambdaQueryWrapper<ProblemEntity> queryWrapper = new LambdaQueryWrapper<>();
        // 查询可见为1的数据
        //noinspection unchecked
        queryWrapper.eq(ProblemEntity::getVisible, ProblemConstant.IsProblemVisible.ABLE_TO_SEE.getCode())
                // 传了难度就筛选出该难度下的题
                .eq(Objects.nonNull(difficulty) &&
                                difficulty >= ProblemConstant.ProblemDifficultyDegree.SIMPLE_PROBLEM.getCode() &&
                                difficulty <= ProblemConstant.ProblemDifficultyDegree.DIFFICULT_PROBLEM.getCode()
                        , ProblemEntity::getDifficulty, difficulty)
                // 默认简单题在前
                .orderByAsc(Objects.isNull(isDescByDifficulty) || !isDescByDifficulty, ProblemEntity::getDifficulty)
                // 选择难题在前
                .orderByDesc(Objects.nonNull(isDescByDifficulty) && isDescByDifficulty, ProblemEntity::getDifficulty)
                // 按照时间倒序排列
                .orderByDesc(ProblemEntity::getCreateTime);
        // 分页
        // 判空
        IPage<ProblemEntity> problemIPage = PageUtils.getPage(pageNum, pageSize, ProblemEntity.class);
        page(problemIPage, queryWrapper);
        // TODO 注意后期传回前端部分需要用Vo重新封装，不能直接传dao实例过去
        PageUtils pageUtils = new PageUtils(problemIPage);
        // 返回带上list数据，和总计
        return ResponseResult.okResult(pageUtils);
    }

    @Override
    public ResponseResult getProblemById(Long id) {

        // TODO 待优化
        ProblemEntity problemEntity = this.getById(id);

        return ResponseResult.okResult(problemEntity);
    }

    @Override
    public ResponseResult getProblemByShowId(Long showId) {
        /* TODO 问题是否可见需要定义为常量 */
        ProblemEntity problem = this.getOne(new LambdaQueryWrapper<ProblemEntity>()
                .eq(ProblemEntity::getShowId, showId)
                .eq(ProblemEntity::getVisible, ProblemConstant.IsProblemVisible.ABLE_TO_SEE.getCode()));

        return ResponseResult.okResult(problem);
    }
}
