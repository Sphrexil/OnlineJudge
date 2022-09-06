package com.oj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class SubmissionEntity {
    private String subTestCase;
    @NotNull(message = "代码不得为空")
    private String code;
    @NotNull(message = "时间限制不得为空")
    private Integer timeLimit;
    @NotNull(message = "内存限制不得为空")
    private Integer memoryLimit;
    private String language;
}
