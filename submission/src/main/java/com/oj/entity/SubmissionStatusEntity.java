package com.oj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@TableName("submission")
public class SubmissionStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 *	提交代码
	 */
	@NotBlank(message = "提交代码不能为空和空串")
	private String code;
	/**
	 *	创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 *	提交状态
	 */
	private String status;
	/**
	 *	编程语言
	 */
	private String language;
	/**
	 *	编译错误信息
	 */
	private String compileError;
	/**
	 *	内存使用
	 */
	@NotNull(message = "不能没有内存限制")
	private Integer memoryUsage;
	/**
	 *	答题时间
	 */
	@NotNull(message = "不能没有时间限制")
	private Integer timeUsage;
	/**
	 *	作者
	 */
	@NotNull(message = "不能无作者")
	private Long author;
	/**
	 *	逻辑删除
	 */
	@TableLogic
	private Integer delFlag;

	/**
	 *  问题题号
	 */
	@NotNull(message = "问题不能为空")
	private Long problemId;

	/**
	 * 	问题示例
	 */
	@TableField(exist = false)
	private String testCase;
}
