package com.oj.TestUtils;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Judge {
    private static final Logger log = LoggerFactory.getLogger(Judge.class);

    public static void main(String[] args) {
        String src = "#include <bits/stdc++.h>\nint main(){int a, b;}";
        String fileName = MD5Utils.digest(src.getBytes(StandardCharsets.UTF_8));
        String exePath = compileProgram(src, fileName);
        deleteTempFile(fileName);
    }

    private static String compileProgram(String src, String fileName) {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        log.info(fileName);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(String.format("%s/file/%s.c", getPWD(),fileName)));
            out.write(src);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String compileCmd =
                String.format("g++ -o %s/file/%s %s/file/%s.c -O2",
                        getPWD(), fileName,
                        getPWD(), fileName);
        log.info(compileCmd);
        Process compile = null;
        try {
            compile = runtime.exec(compileCmd);
            BufferedReader error = new BufferedReader(new InputStreamReader(compile.getErrorStream()));
            String compileInfo;
            StringBuilder sb = new StringBuilder();
            while ((compileInfo = error.readLine()) != null) {
                sb.append(compileInfo);
            }
            if(sb.toString().length() != 0){
                throw new Exception(sb.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return String.format("%s/file/%s", getPWD(), fileName);
    }

    private static TestResult testProgram(TestParam testParam){
        Runtime runtime = Runtime.getRuntime();
        Process process;
        String rules = getRules(testParam);
        log.info(rules, testParam);
        try{
            String password = "2002";
            String[] cmd = {"/bin/bash",
                            "-c",
                            String.format("echo %s | sudo -S %s/judger/libjudger.so %s",
                                password, pwd, rules)
            };
            log.info(Arrays.toString(cmd));
            process = runtime.exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String inline;
            while((inline = bufferedReader.readLine()) != null){
                result.append(inline);
            }
            log.info(result.toString());
            TestResult testResult = JSONUtil.parseObject(result.toString(), TestResult.class);
            log.info(testResult.toString());
            return testResult;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteTempFile(String fileName){
        log.info(String.format("delete file %s", fileName));
        File exe = new File(String.format("%s/file/%s", pwd, fileName));
        File cFile = new File(String.format("%s/file/%s.c", pwd, fileName));
        boolean deleted = exe.delete();
        deleted = cFile.delete();
    }
    private static String getRules(TestParam testParam){
        return String.format(
                "--max_cpu_time=%d "
                        +"--max_real_time=%d "
                        +"--max_memory=%d "
                        +"--max_stack=%d "
                        +"--max_output_size=%d "
                        +"--max_process_number=%d "
                        +"--uid=%d "
                        +"--gid=%d "
                        +"--memory_limit_check_only=%d "
                        +"--exe_path=%s "
                        +"--input_path=%s "
                        +"--output_path=%s "
                        +"--error_path=%s "
                        +"--log_path=%s "
                        +"--seccomp_rule_name=%s "
                ,
                testParam.getMaxCpuTime(),
                testParam.getMaxMemory(),
                testParam.getMaxRealTime(),
                testParam.getMaxStack(),
                testParam.getMaxOutputSize(),
                testParam.getMaxProcessNumber(),
                testParam.getUid(),
                testParam.getGid(),
                testParam.getMemoryLimitCheckOnly(),
                testParam.getExePath(),
                testParam.getInputPath(),
                testParam.getOutputPath(),
                testParam.getErrorPath(),
                testParam.getLogPath(),
                testParam.getSeccompRuleName()
        );
    }

    private static String pwd = null;

    private static String getPWD() {
        if(pwd == null) {
            Runtime runtime = Runtime.getRuntime();
            Process process;
            try {
                process = runtime.exec("pwd");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inline;
                while((inline = bufferedReader.readLine()) != null){
                    result.append(inline);
                }
                pwd = result.toString();
            }catch (IOException e){
                throw new RuntimeException();
            }
        }
        return pwd;
    }
}
