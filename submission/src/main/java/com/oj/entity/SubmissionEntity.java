package com.oj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-08-27 16:37:04
 */
@Data
@TableName("submission")
public class SubmissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 *
	 */
	private String code;
	/**
	 *
	 */
	private Date createTime;
	/**
	 *
	 */
	private String status;
	/**
	 *
	 */
	private String language;
	/**
	 *
	 */
	private String compileError;
	/**
	 *
	 */
	private Integer memoryUsage;
	/**
	 *
	 */
	private Integer timeUsage;
	/**
	 *
	 */
	private String author;

}
