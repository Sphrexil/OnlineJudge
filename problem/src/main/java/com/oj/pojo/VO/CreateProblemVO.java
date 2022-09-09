package com.oj.pojo.VO;

import com.oj.entity.ProblemEntity;
import com.oj.entity.TestCaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProblemVO {
    @NotNull
    private ProblemEntity problem;
    @NotNull
    private ArrayList<TestCaseEntity> cases;
}
