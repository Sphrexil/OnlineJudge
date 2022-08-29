package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oj.dao.ProblemDao;
import com.oj.entity.ProblemEntity;
import com.oj.service.ProblemService;
import com.oj.utils.PageUtils;
import com.oj.utils.ResponseResult;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemDao, ProblemEntity> implements ProblemService {

    @Override
    public ResponseResult getProblemList(Integer pageNum, Integer pageSize) {

        // 查询
        LambdaQueryWrapper<ProblemEntity> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 需要将此处的是否可见设置到公共模块的常量中去
        // 查询可见为1的数据
        queryWrapper.eq(ProblemEntity::getVisible, 1)
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
}
