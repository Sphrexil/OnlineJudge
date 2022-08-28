package com.oj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-08-27 16:35:57
 */
@Data
@TableName("problem")
public class ProblemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
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
	private String testCase;
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
	private Integer del_flag;
	/**
	 *
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 *
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	/**
	 * 题目难度
	 */
	private String difficulty;
	/**
	 * 数据范围

	 */
	private String dataRange;
	/**
	 * 时空限制
	 */
	private Integer memoryLimit;
}
