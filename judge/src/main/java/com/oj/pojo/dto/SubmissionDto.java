package com.oj.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: SubmissionVo
 * date: 2022/8/31 19:33
 * author: zhenyu
 * version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubmissionDto {
    private String subTestCase;
    private String code;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String language;
}
