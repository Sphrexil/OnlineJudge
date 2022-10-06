package com.oj.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: TagListDto
 * date: 2022/9/11 17:24
 * author: zhenyu
 * version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    //备注
    private String remark;
    //标签名
    private String name;
}
