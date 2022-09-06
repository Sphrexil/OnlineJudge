package com.oj.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResultVo {

    @JsonProperty("cpu_time")
    private Integer cpuTime;
    @JsonProperty("real_time")
    private Integer realTime;
    @JsonProperty("memory")
    private Integer memory;
    @JsonProperty("signal")
    private Integer signal;
    @JsonProperty("exit_code")
    private Integer exitCode;
    private Integer error;
    private Integer result;
    private String lastStatus;
}
