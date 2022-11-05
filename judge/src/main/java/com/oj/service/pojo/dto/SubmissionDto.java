package com.oj.service.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oj.TestUtils.SingleCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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
