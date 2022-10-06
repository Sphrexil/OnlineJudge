package com.oj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    private String content;
    private Long categoryId;
    private String title;
    private String summary;
    private Long viewCount;
    private Boolean isComment;
    private Long createBy;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    private List<String> tags;
}
