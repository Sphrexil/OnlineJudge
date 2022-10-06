package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.ArticleEntity;
import com.oj.pojo.dto.ArticleListDto;
import com.oj.pojo.vo.ArticleVo;
import com.oj.utils.ResponseResult;


public interface ArticleService extends IService<ArticleEntity> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult commitArticle(ArticleVo article);

    ResponseResult queryMyArticles(Long useId);

    ResponseResult deleteMyArticle(Long articleId);

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);
}
