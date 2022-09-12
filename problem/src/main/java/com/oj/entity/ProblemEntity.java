package com.oj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("problem")
public class ProblemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty(value = "表id", hidden = true)
	private Long id;
	/**
	 * 问题显示的名字
	 */
	private String problemName;
	/**
	 * 问题主干
	 */
	private String problem;
	/**
	 * 输出描述
	 */
	private String outputDescribe;
	/**
	 * 输入描述
	 */
	private String inputDescribe;
	/**
	 * 测试样例--JSON
	 */
	private String sample;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 是否可见

	 */
	private Integer visible;
	/**
	 * 软删除
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty(value = "逻辑删除", hidden = true)
	private Integer isDel;
	/**
	 *
	 */
	@ApiModelProperty(value = "创建时间", hidden = true)
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 *
	 */
	@ApiModelProperty(value = "修改时间", hidden = true)
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	/**
	 * 题目难度
	 */
	private Integer difficulty;
	/**
	 * 数据范围

	 */
	private String dataRange;
	/**
	 * 时空限制
	 */
	private Integer memoryLimit;
	/**
	 * 时间限制
	 */
	private Integer timeLimit;

	private String showId;
}
