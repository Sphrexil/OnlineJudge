package com.oj.TestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author zzy
 */
@Data
public class TestParam {
    /**
     * 以毫秒计算
     */
    private Integer maxCpuTime;

    /**
     * 以毫秒计算
     */
    private Integer maxRealTime;

    /**
     * 以字节计算
     */
    private Integer maxMemory;

    /**
    * 以字节计算
    */
    private Integer maxStack;

    /**
     * 以字节计算
     */
    private Integer maxOutputSize;

    /**
     * 以个数计算
     */
    private Integer maxProcessNumber;

    /**
     * 暂时用不上
     */
    @JsonIgnore
    private Integer uid;

    /**
     * 暂时用不上
     */
    @JsonIgnore
    private Integer gid;

    /**
     * 暂时用不上，不知道干啥用
     */
    @JsonIgnore
    private Integer memoryLimitCheckOnly;

    /**
     * 可执行文件路径
     */
    private String exePath;
    /**
     * 输入文件路径
     */
    private String inputPath;
    /**
     * stdout输出文件路径
     */
    private String outputPath;
    /**
     * stderr输出路径
     */
    private String errorPath;
    /**
     * 日志路径
     */
    private String logPath;
    /**
     * 判题规则
     */
    private String seccompRuleName;
}
