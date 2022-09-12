package com.oj.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2022-09-12 14:21:14
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserEntity {
    //主键@TableId
    @TableId
    @ApiModelProperty(value = "表id", hidden = true)
    private Long id;

    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（1正常 0停用）
    private String status;
    //邮箱
    private String email;
    //手机号
    private String phoneNumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //创建人的用户id
    private Long createBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "逻辑删除", hidden = true)
    private Integer isDel;
    //用户所通过的题目
    @TableField(exist = false)
    private HashSet<Long> acceptedProblemSet;

    @TableField("accepted_problem")
    @ApiModelProperty(value = "数据库通过问题", hidden = true)
    private String acceptedProblemToDb;
}
