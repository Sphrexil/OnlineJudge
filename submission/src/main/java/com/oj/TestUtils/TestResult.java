package com.oj.TestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author zzy
 */
@Data
public class TestResult {

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
}
