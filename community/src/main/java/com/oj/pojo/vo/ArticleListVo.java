package com.oj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;
    //作者
    private Long createBy;
    //昵称
    private String nickName;
    //头像
    private String avatar;
    //访问量
    private Long viewCount;

    private Date createTime;


}
