package com.oj.TestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zzy
 */
@Data
public class TestParam {
    public TestParam(JudgingArgument judgingArgument, String fileName, String pwd){
        this.maxMemory = judgingArgument.getMemoryLimit() * 1024 * 1024;
        this.maxCpuTime = judgingArgument.getTimeLimit();
        this.maxRealTime = this.maxCpuTime * 2;
        this.maxStack = this.maxMemory;
        this.maxOutputSize = 1024 * 1024;
        this.maxProcessNumber = 200;
        this.exePath = String.format("%s/file/bin/%s", pwd, fileName);
        this.inputPath = String.format("%s/file/in/%s.in", pwd, fileName);
        this.outputPath = String.format("%s/file/out/%s.out", pwd, fileName);
        this.errorPath = String.format("%s/file/error/%s.err", pwd, fileName);
        this.logPath = String.format("%s/file/log.log", pwd);
    }
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
    private Integer uid = 0;

    /**
     * 暂时用不上
     */
    @JsonIgnore
    private Integer gid = 0;

    /**
     * 始终为1即可
     */
    @JsonIgnore
    private Integer memoryLimitCheckOnly = 1;

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
}
