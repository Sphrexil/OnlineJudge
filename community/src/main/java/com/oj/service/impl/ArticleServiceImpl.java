package com.oj.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.Dao.ArticleDao;
import com.oj.Dao.CategoryDao;
import com.oj.constants.ArticleConstants;
import com.oj.entity.ArticleEntity;
import com.oj.entity.CategoryEntity;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.feign.ArticleUseFeignService;
import com.oj.pojo.dto.ArticleListDto;
import com.oj.pojo.vo.*;
import com.oj.service.ArticleService;
import com.oj.user.UserEntity;
import com.oj.utils.BeanCopyUtils;
import com.oj.utils.PageUtils;
import com.oj.utils.RedisCache;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Autowired
    private CategoryDao categoryMapper;
    @Autowired
    private ArticleUseFeignService feignService;
    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<ArticleEntity> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(ArticleEntity::getStatus, ArticleConstants.ARTICLE_STATUS_NORMAL).eq(ArticleEntity::getDelFlag, ArticleConstants.ARTICLE_DEL_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(ArticleEntity::getViewCount);
        //最多只查询10条
        Page<ArticleEntity> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<ArticleEntity> articles = page.getRecords();
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<ArticleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, ArticleEntity::getCategoryId, categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(ArticleEntity::getStatus, ArticleConstants.ARTICLE_STATUS_NORMAL);

        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(ArticleEntity::getIsTop);

        IPage<ArticleEntity> listVoIPage = PageUtils.getPage(pageNum, pageSize, ArticleEntity.class);
        //分页查询
        page(listVoIPage, lambdaQueryWrapper);

        PageUtils<ArticleEntity> pageUtils = new PageUtils<>(listVoIPage);
        List<ArticleEntity> articles = pageUtils.getList();
        //查询categoryName
        articles.stream().map(article -> article.setCategoryName(
                categoryMapper.selectById(article.getCategoryId()).getName())).collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }


        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(pageUtils.getList(), ArticleListVo.class);

        for (ArticleListVo articleListVo : articleListVos) {
            Long userId = articleListVo.getCreateBy();
            UserEntity user = (UserEntity) feignService.getUserById(userId).getData(new TypeReference<UserEntity>(){});
            articleListVo.setAvatar(user.getAvatar());
            articleListVo.setNickName(user.getNickName());
        }


        PageUtils<ArticleListVo> pageVo = new PageUtils<>(articleListVos, pageUtils.getTotalCount(), pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {


        //根据id查询文章
        ArticleEntity article = getById(id);
        if (!article.getDelFlag().equals(ArticleConstants.ARTICLE_DEL_STATUS_NORMAL)) {
            throw new SystemException(ResultCode.Article_NOT_EXIST);
        }
        //从redis中获取
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        CategoryEntity category = categoryMapper.selectById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        ArticleEntity article = getById(id);
        if (!article.getDelFlag().equals(ArticleConstants.ARTICLE_DEL_STATUS_NORMAL)) {
            throw new SystemException(ResultCode.Article_NOT_EXIST);
        }
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        //封装返回
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult commitArticle(ArticleVo article) {

        if (Objects.isNull(article)) {
            throw new RuntimeException();
        }
        Long categoryId = article.getCategoryId();
        CategoryEntity category = categoryMapper.selectById(categoryId);
        ArticleEntity relArticle = BeanCopyUtils.copyBean(article, ArticleEntity.class);
        relArticle.setCategoryName(category.getName());
        //DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.baseMapper.insert(relArticle);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult queryMyArticles(Long userId) {

        //查redis

        // 判空
        if (Objects.isNull(userId)) throw new RuntimeException("请传入用户id");
        UserEntity user = (UserEntity) feignService.getUserById(userId).getData(new TypeReference<UserEntity>(){});
        if (Objects.isNull(user)) throw new SystemException(ResultCode.USER_NOT_EXIST);

        // 查询
        List<ArticleEntity> articleList = this.baseMapper.selectList(new LambdaQueryWrapper<ArticleEntity>().eq(ArticleEntity::getCreateBy, userId)
                // 查询出未被删除的
                .eq(ArticleEntity::getDelFlag, ArticleConstants.ARTICLE_DEL_STATUS_NORMAL));

        // 封装
        List<ArticleEntity> list = articleList.stream().map(article -> {
            Long categoryId = article.getCategoryId();
            CategoryEntity category = categoryMapper.selectById(categoryId);
            String name = category.getName();
            article.setCategoryName(name);
            return article;
        }).collect(Collectors.toList());

        List<ArticleUserVo> articleUserVos = BeanCopyUtils.copyBeanList(list, ArticleUserVo.class);


        return ResponseResult.okResult(articleUserVos);
    }

    @Override
    @Transactional
    public ResponseResult deleteMyArticle(Long articleId) {

        if (Objects.isNull(articleId)) throw new RuntimeException("请传入文章");
        ArticleEntity article = this.baseMapper.selectById(articleId);
        if (Objects.isNull(article)) throw new SystemException(ResultCode.Article_NOT_EXIST);

        removeById(articleId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<ArticleEntity> wrapper = new LambdaQueryWrapper<ArticleEntity>().eq(StringUtils.hasText(articleListDto.getTitle()), ArticleEntity::getTitle, articleListDto.getTitle()).eq(StringUtils.hasText(articleListDto.getSummary()), ArticleEntity::getSummary, articleListDto.getSummary());
        IPage<ArticleEntity> ArticlePage = PageUtils.getPage(pageNum, pageSize, ArticleEntity.class);
        page(ArticlePage, wrapper);
        PageUtils<ArticleEntity> pageUtils = new PageUtils<>(ArticlePage);
        return ResponseResult.okResult(pageUtils);
    }
}
