package com.oj.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: ArticleListDto
 * date: 2022/9/11 21:27
 * author: zhenyu
 * version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    private String title;
    private String summary;
}
