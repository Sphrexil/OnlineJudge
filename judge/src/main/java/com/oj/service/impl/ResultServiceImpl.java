package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.dao.ResultDao;
import com.oj.entity.ResultEntity;
import com.oj.service.ResultService;
import org.springframework.stereotype.Service;

/**
 * (Result)表服务实现类
 *
 * @author makejava
 * @since 2022-09-06 09:11:07
 */
@Service
public class ResultServiceImpl extends ServiceImpl<ResultDao, ResultEntity> implements ResultService {

}
