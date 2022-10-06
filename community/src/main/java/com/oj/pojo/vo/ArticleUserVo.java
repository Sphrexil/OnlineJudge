package com.oj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUserVo {
    private Long id;
    private String title;
    private Long categoryId;
    private String categoryName;
}
