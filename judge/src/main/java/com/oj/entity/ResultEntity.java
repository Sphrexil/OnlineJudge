package com.oj.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Result)表实体类
 *
 * @author makejava
 * @since 2022-09-06 09:11:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("result")
public class ResultEntity  {
    @TableId
    private Long id;


    private String resultOfSubmission;

    private String relatedUser;

    private Integer relatedProblem;

    private Integer relatedSubmission;

    private Integer isDel;

    private Integer createTime;

}

