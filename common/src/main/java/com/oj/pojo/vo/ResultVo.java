package com.oj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: ResultVo
 * date: 2022/9/6 14:15
 * author: zhenyu
 * version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo {

    private Integer memoryUsage;

    private Integer timeUsage;

    private String compileError;

    private String status;
}
