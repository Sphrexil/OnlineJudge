package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.Dao.CategoryDao;
import com.oj.constants.ArticleConstants;
import com.oj.constants.CategoryConstants;
import com.oj.entity.ArticleEntity;
import com.oj.entity.CategoryEntity;
import com.oj.pojo.vo.CategoryVo;
import com.oj.service.ArticleService;
import com.oj.service.CategoryService;
import com.oj.utils.BeanCopyUtils;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-02-02 12:29:52
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<ArticleEntity> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(ArticleEntity::getStatus, ArticleConstants.ARTICLE_STATUS_NORMAL);
        List<ArticleEntity> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<CategoryEntity> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> CategoryConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}

