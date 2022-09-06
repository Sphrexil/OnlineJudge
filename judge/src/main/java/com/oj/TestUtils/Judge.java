package com.oj.TestUtils;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Judge {
    private static final Logger log = LoggerFactory.getLogger(Judge.class);
    public static TestResult process(JudgingArgument judgingArgument){
        String src = judgingArgument.getCodeSrc();
        String fileName = MD5Utils.digest(src.getBytes(StandardCharsets.UTF_8));
        compileProgram(src, fileName);
        TestParam testParam = new TestParam(judgingArgument, fileName, getPWD());
        log.info("rules " + testParam.toString());
        for(int i = 0;i < judgingArgument.getTestCases().size();i ++){
            try {
                writeCaseIO(judgingArgument.getTestCases().get(i).getIn(),
                        judgingArgument.getTestCases().get(i).getOut(),
                        fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            TestResult testResult = testProgram(testParam);
            log.info(testResult.toString());
        }
        deleteTempFile(fileName);
        return null;
    }
    private static void writeCaseIO(String in, String out, String fileName) throws IOException {
        BufferedWriter caseInWriter = new BufferedWriter(
                new FileWriter(String.format("%s/file/in/%s.in", getPWD(), fileName)));
        caseInWriter.write(in);
        caseInWriter.flush();caseInWriter.close();

        BufferedWriter caseOutWriter = new BufferedWriter(
                new FileWriter(String.format("%s/file/checker/%s.out", getPWD(), fileName)));
        caseOutWriter.write(out);
        caseOutWriter.flush();caseOutWriter.close();
    }
    private static void compileProgram(String src, String fileName) {
        Runtime runtime = Runtime.getRuntime();
        log.info(String.format("%s/file/src/%s.cpp", getPWD(),fileName));
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(String.format("%s/file/src/%s.cpp", getPWD(),fileName)));
            out.write(src);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String compileCmd =
                String.format("g++ -o %s/file/bin/%s %s/file/src/%s.cpp -O2",
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
                sb.append("\n");
            }
            if(sb.toString().length() != 0){
                throw new Exception(sb.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
        File exe = new File(String.format("%s/file/bin/%s", pwd, fileName));
        File cFile = new File(String.format("%s/file/src/%s.cpp", pwd, fileName));
        log.info(String.format("try to delete file %s", fileName));
        boolean deleted = exe.delete();
        if(!deleted){
            log.warn(String.format("%s binary haven't been deleted", fileName));
        }
        deleted = cFile.delete();
        if(!deleted){
            log.warn(String.format("%s src haven't been deleted", fileName));
        }
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
                ,
                testParam.getMaxCpuTime(),
                testParam.getMaxRealTime(),
                testParam.getMaxMemory(),
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
                testParam.getLogPath()
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
            log.info(pwd);
        }
        return pwd;
    }
}
