package com.oj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("submission")
public class SubmissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 *	提交代码
	 */
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
	private Integer memoryUsage;
	/**
	 *	答题时间
	 */
	private Integer timeUsage;
	/**
	 *	作者
	 */
	private Long author;
	/**
	 *	逻辑删除
	 */
	@TableLogic
	private Integer delFlag;

	/**
	 *  问题题号
	 */
	private Long problemId;

	/**
	 * 	问题示例
	 */
	@TableField(exist = false)
	private String testCase;
}
