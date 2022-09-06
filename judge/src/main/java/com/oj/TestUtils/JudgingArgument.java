package com.oj.TestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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
    private ArrayList<SingleCase> testCases = new ArrayList<>();
}