package com.oj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@TableName("submission")
public class SubmissionStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty(value = "表id", hidden = true)
	private Long id;
	/**
	 *	提交代码
	 */
	@NotEmpty(message = "提交代码为空或空串")
	private String code;
	/**
	 *	创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间", hidden = true)
	private Date createTime;
	/**
	 *	提交状态
	 */
	@ApiModelProperty(value = "返回的状态", hidden = true)
	private String status;
	/**
	 *	编程语言
	 */
	private String language;
	/**
	 *	编译错误信息
	 */
	@ApiModelProperty(value = "返回的错误", hidden = true)
	private String error;
	/**
	 *	内存使用
	 */
	@ApiModelProperty(value = "内存使用", hidden = true)
	private Integer memoryUsage;
	/**
	 *	答题时间
	 */
	@ApiModelProperty(value = "时间使用", hidden = true)
	private Integer timeUsage;
	/**
	 *	作者
	 */
	@NotNull(message = "作者为空")
	private Long relatedUser;
	/**
	 *	逻辑删除
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty(value = "逻辑删除", hidden = true)
	private Integer isDel;

	/**
	 *  问题题号
	 */
	@NotNull(message = "问题为空")
	private Long relatedProblem;

	/**
	 * 	问题示例
	 */
	@TableField(exist = false)
	private String testCase;

	@TableField(exist = false)
	@NotNull
	private Boolean isDebug;
}
