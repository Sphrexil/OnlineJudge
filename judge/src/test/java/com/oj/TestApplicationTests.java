package com.oj;


import com.oj.TestUtils.Judge;
import com.oj.TestUtils.JudgingArgument;
import com.oj.TestUtils.SingleCase;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestApplicationTests.class)
public class TestApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(TestApplicationTests.class);

    @Test
    void testJudge(){
        JudgingArgument judgingArgument = new JudgingArgument();
        judgingArgument.setCodeSrc(
                "#include <iostream> \nint main(){std::cout << 1 << std::endl;}"
        );
        judgingArgument.setTimeLimit(1000);
        judgingArgument.setMemoryLimit(128);
        judgingArgument.getTestCases().add(new SingleCase(
            "", "1"
        ));
        Judge.process(judgingArgument);
    }
}
