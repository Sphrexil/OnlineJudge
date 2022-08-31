package com.oj.TestUtils;

import java.io.*;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springfox.documentation.spring.web.json.Json;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

    public static void main(String[] args) {
//        Runtime runtime = Runtime.getRuntime();
//        Process process;
//        try {
//            String rules = String.format(
//                    "--max_cpu_time=%d "
//                            + "--max_real_time=%d "
//                            + "--max_memory=%d "
//                            + "--max_stack=%d "
//                            + "--max_output_size=%d "
//                            + "--max_process_number=%d "
//                            + "--uid=%d "
//                            + "--gid=%d "
//                            + "--memory_limit_check_only=%d "
//                            + "--exe_path=%s "
//                            + "--input_path=%s "
//                            + "--output_path=%s "
//                            + "--error_path=%s "
//                            + "--log_path=%s "
//                            + "--seccomp_rule_name=%s "
//                    ,
//                    1000, // cpu_time
//                    2000, // max_real_time
//                    128 * 1024 * 1024, // max_memory
//                    32 * 1024 * 1024, // max_stack
//                    10000, //max_output_size
//                    200, // max_process_number
//                    0, // uid //useless
//                    0, // pid //useless
//                    0, // idk
//                    "./file/main", // binary file path
//                    "./file/1.in", // IN file
//                    "./file/1.out", //OUT file
//                    "./file/1.out", //error path
//                    "./file/log.log", // logging path
//                    "c_cpp" //language
//            );
////            System.out.println(rules);
//
//
//
////        log.info(Arrays.toString(cmd));
////        process = runtime.exec(cmd);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        StringBuilder result = new StringBuilder();
//        String inline;
//        while ((inline = bufferedReader.readLine()) != null) {
//            result.append(inline);
//        }
//        TestResult testResult = JSONUtil.parseObject(result.toString(), TestResult.class);
//        System.out.println(testResult);
//
//    } catch (IOException e) {
//
//        }
    }
}
    