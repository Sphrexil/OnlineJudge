package com.oj.entity;



import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.oj.pojo.vo.TestResultVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
    public void getFinalResult(){
        for(TestResultVo i : resultOfCase){

        }
    }
    @TableId
    private Long id;

    private ArrayList<TestResultVo> resultOfCase;

    @NotNull
    private Long relatedUser;

    @NotNull
    private Long relatedProblem;

    @NotNull
    private Long relatedSubmission;

    @TableLogic
    private Integer isDel;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}

