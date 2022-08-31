package com.oj.TestUtils;

import lombok.Data;

/**
 * @author zzy
 */
@Data
public class JsonTestParam {
    private Integer timeLimit;
    private Integer memoryLimit;
    private String codeType;
    private String codeSrc;
    private SingleCase[] testCases;
}
@Data
class SingleCase{
    String in;
    String out;
}