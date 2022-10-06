package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.CategoryEntity;
import com.oj.utils.ResponseResult;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-04-04 14:26:32
 */
public interface CategoryService extends IService<CategoryEntity> {

    ResponseResult getCategoryList();
}


