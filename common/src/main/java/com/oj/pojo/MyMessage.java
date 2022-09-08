package com.oj.pojo;

import com.oj.pojo.vo.SubmissionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * description: MyMessage
 * date: 2022/9/8 13:43
 * author: zhenyu
 * version: 1.0
 */


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyMessage <T> {
    private T msg;
    private String token;
}
