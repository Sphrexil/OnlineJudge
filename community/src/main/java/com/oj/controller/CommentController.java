package com.oj.controller;


import com.oj.constants.CommentConstants;
import com.oj.entity.CommentEntity;
import com.oj.service.CommentService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/commentList")
    public ResponseResult commentList(Integer commentType,Long problemOrArticleId,Integer pageNum,Integer pageSize){


        return commentService.commentList(commentType, problemOrArticleId, pageNum, pageSize);
    }
    @PostMapping
    public ResponseResult addComment(@RequestBody CommentEntity comment, HttpServletRequest request){


        return commentService.addComment(comment, request);
    }
//    @GetMapping("/linkCommentList")
//    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
//        return  commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
//    }

}
