package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.dao.ProblemDao;
import com.oj.entity.ProblemEntity;
import com.oj.pojo.vo.PageVo;
import com.oj.service.ProblemService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Objects;


@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemDao, ProblemEntity> implements ProblemService {

    @Override
    public ResponseResult getProblemList(Integer pageNum, Integer pageSize) {
        // 判空
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize) || pageNum.equals(0) || pageSize.equals(0)) {
           throw new RuntimeException("页码或者页码大小不能为空或0");
           // TODO 要将错误信息统一到ResultCode中去
        }
        // 查询
        LambdaQueryWrapper<ProblemEntity> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 需要将此处的是否可见设置到公共模块的常量中去
        // 查询可见为1的数据
        queryWrapper.eq(ProblemEntity::getVisible, 1)
                    // 按照时间倒序排列
                    .orderByDesc(ProblemEntity::getCreateTime);
        // 分页
        Page<ProblemEntity> problemPage = new Page<>(pageNum, pageSize);
        page(problemPage, queryWrapper);
        // TODO 注意后期传回前端部分需要用Vo重新封装，不能直接传dao实例过去
        List<ProblemEntity> problemList = problemPage.getRecords();
        long total = problemPage.getTotal();
        PageVo pageVo = new PageVo(problemList, total);
        // 返回带上list数据，和总计
        return ResponseResult.okResult(pageVo);
    }
}
