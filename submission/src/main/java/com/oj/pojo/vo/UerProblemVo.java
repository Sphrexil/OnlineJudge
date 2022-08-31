package com.oj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * description: UerProblemVo
 * date: 2022/8/30 17:23
 * author: zhenyu
 * version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UerProblemVo implements Serializable {
    private Long author;
    private Long problemId;
    private String language;
}
