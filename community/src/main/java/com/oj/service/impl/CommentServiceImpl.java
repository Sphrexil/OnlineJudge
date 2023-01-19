package com.oj.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.Dao.CommentDao;
import com.oj.constants.CommentConstants;
import com.oj.entity.CommentEntity;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.feign.ArticleUseFeignService;
import com.oj.feign.CommentProblemFeignService;
import com.oj.pojo.dto.CommentProblemDto;
import com.oj.pojo.vo.CommentVo;
import com.oj.service.CommentService;
import com.oj.user.UserEntity;
import com.oj.utils.BeanCopyUtils;
import com.oj.utils.JwtUtil;
import com.oj.utils.PageUtils;
import com.oj.utils.ResponseResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.events.CommentEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 评论表(SgComment)表服务实现类
 *
 * @author makejava
 * @since 2022-04-09 15:29:55
 */


@Service("CommentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private ArticleUseFeignService feignService;
    @Autowired
    private CommentProblemFeignService problemFeignService;

    @Override
    public ResponseResult commentList(Integer commentType, Long problemOrArticleId, Integer pageNum, Integer pageSize) {


        //获取根评论


        //通过文章Id获取评论
        LambdaQueryWrapper<CommentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentConstants.ARTICLE_COMMENT_TYPE.equals(commentType), CommentEntity::getArticleId, problemOrArticleId);
//        if (CommentConstants.PROBLEM_COMMENT_TYPE.equals(commentType)) {
//            CommentProblemDto problem = (CommentProblemDto) problemFeignService.getProblemById(problemOrArticleId).getData(new TypeReference<CommentProblemDto>() {
//            });
//        }
        queryWrapper.eq(CommentConstants.PROBLEM_COMMENT_TYPE.equals(commentType), CommentEntity::getProblemId, problemOrArticleId);
        //根评论的rootId为-1
        //评论类型
        queryWrapper.eq(CommentEntity::getRootId, CommentConstants.COMMENT_ROOT);
//        queryWrapper.eq(CommentEntity::getType, commentType);
        //分页查询
        IPage<CommentEntity> iPage = PageUtils.getPage(pageNum, pageSize, CommentEntity.class);

        page(iPage, queryWrapper);
        //用Vo进行封装
        PageUtils pageUtils = new PageUtils(iPage);
        List<CommentVo> commentVoList = toCommentVoList(pageUtils.getList());

        //查询所有的子评论集合，并且赋值给对应的属性
        commentVoList
                .forEach(commentVo -> {

                    commentVo.setChildren(getChildrenList(commentVo.getId()));
                });
        pageUtils.setList(commentVoList);
        return ResponseResult.okResult(pageUtils);
    }

    @Override
    public ResponseResult addComment(CommentEntity comment, HttpServletRequest request) {
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(ResultCode.NULL_CONTENT);
        } else if(Objects.isNull(request.getHeader("token"))) {
            throw new SystemException(ResultCode.USER_ACCOUNT_EXPIRED);
        }
        String token = request.getHeader("token");
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
            String userId = claims.getSubject();
            comment.setCreateBy(Long.valueOf(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        save(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMaxId() {

        Long maxId = this.baseMapper.getMaxId();
        return ResponseResult.okResult(maxId);
    }

    private List<CommentVo> getChildrenList(Long id) {
        LambdaQueryWrapper<CommentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentEntity::getRootId, id);
        queryWrapper.orderByAsc(CommentEntity::getCreateTime);
        return toCommentVoList(list(queryWrapper));
    }

    private List<CommentVo> toCommentVoList(List<CommentEntity> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        commentVos.forEach(commentVo -> {
            UserEntity user = (UserEntity) feignService.getUserById(commentVo.getCreateBy()).getData(new TypeReference<UserEntity>() {
            });
            UserEntity toCommentUser = (UserEntity) feignService.getUserById(commentVo.getToCommentUserId()).getData(new TypeReference<UserEntity>() {
            });
            commentVo.setUsername(user.getNickName());
            commentVo.setAvatar(user.getAvatar());
            if (commentVo.getToCommentId() != -1) {
                commentVo.setToCommentUserName(toCommentUser.getNickName());
            }
        });
        return commentVos;
    }

}
