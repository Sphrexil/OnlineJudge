package com.oj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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
public class UerProblemListVo implements Serializable {
    @NotNull(message = "作者不能为空")
    private Long relatedUser;

    private Long problemId;

    private String language;

    private Integer pageNum;

    private Integer pageSize;
}
