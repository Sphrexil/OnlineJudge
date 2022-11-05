package com.oj.TestUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.oj.service.pojo.dto.SubmissionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * @author zzy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class JudgingArgument {
    public JudgingArgument(SubmissionDto submissionDto){
        this.timeLimit = submissionDto.getTimeLimit();
        this.codeSrc = submissionDto.getCode();
        this.memoryLimit = submissionDto.getMemoryLimit();
        this.testCases = JSONUtil.parseObject(submissionDto.getSubTestCase(), new TypeReference<ArrayList<SingleCase>>(){});
    }
    private Integer timeLimit;
    private Integer memoryLimit;
    private String codeType;
    private String codeSrc;
    private ArrayList<SingleCase> testCases = new ArrayList<>();
}