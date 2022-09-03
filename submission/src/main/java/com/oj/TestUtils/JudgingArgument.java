package com.oj.TestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgingArgument {
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