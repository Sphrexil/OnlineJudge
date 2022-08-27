package com.oj.service.impl;

import com.oj.dao.ProblemDao;
import com.oj.entity.ProblemEntity;
import com.oj.service.ProblemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemDao, ProblemEntity> implements ProblemService {

}
