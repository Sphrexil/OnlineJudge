package com.oj.TestUtils;

import com.oj.pojo.vo.TestResultVo;
import com.oj.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Judge {
    private static final Logger log = LoggerFactory.getLogger(Judge.class);
    public static TestResultVo process(JudgingArgument judgingArgument) throws CompileException, IOException {
        checkFolderExists();
        String src = judgingArgument.getCodeSrc();
        String fileName = MD5Utils.GetStringMD5(src.getBytes(StandardCharsets.UTF_8));
        compileProgram(src, fileName);
        TestParam testParam = new TestParam(judgingArgument, fileName, getPWD());
        TestResultVo testResultVo = new TestResultVo();
        log.info("rules " + testParam.toString());
        for(int i = 0;i < judgingArgument.getTestCases().size();i ++){
            writeCaseIO(judgingArgument.getTestCases().get(i).getIn(), fileName);
            testResultVo = testProgram(testParam, judgingArgument.getTestCases().get(i).getOut());
        }
        deleteTempFile(fileName);
        return testResultVo;
    }
    private static void writeCaseIO(String in, String fileName) throws IOException {
        BufferedWriter caseInWriter = new BufferedWriter(
                new FileWriter(String.format("%s/file/in/%s.in", getPWD(), fileName)));
        caseInWriter.write(in);
        caseInWriter.flush();caseInWriter.close();
    }
    private static void compileProgram(String src, String fileName) throws CompileException, IOException {
        Runtime runtime = Runtime.getRuntime();
        log.info(String.format("%s/file/src/%s.cpp", getPWD(),fileName));
        BufferedWriter out = new BufferedWriter(new FileWriter(String.format("%s/file/src/%s.cpp", getPWD(),fileName)));
        out.write(src);
        out.close();

        String compileCmd =
                String.format("g++ -o %s/file/bin/%s %s/file/src/%s.cpp -O2",
                        getPWD(), fileName,
                        getPWD(), fileName);
        log.info(compileCmd);
        Process compile = null;
        compile = runtime.exec(compileCmd);
        BufferedReader error = new BufferedReader(new InputStreamReader(compile.getErrorStream()));
        String compileInfo;
        StringBuilder sb = new StringBuilder();
        while ((compileInfo = error.readLine()) != null) {
            sb.append(compileInfo);
            sb.append("\n");
        }
        if(sb.toString().length() != 0){
            deleteTempFile(fileName);
            throw new CompileException(sb.toString());
        }
    }

    private static TestResultVo testProgram(TestParam testParam, String stdout){
        Runtime runtime = Runtime.getRuntime();
        Process process;
        String rules = getRules(testParam);
        try{
            String password = "2002";
            String[] cmd = {"/bin/bash",
                            "-c",
                            String.format("echo %s | sudo -S %s/judger/libjudger.so %s",
                                password, pwd, rules)
            };
            process = runtime.exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String inline;
            while((inline = bufferedReader.readLine()) != null){
                result.append(inline);
            }
            TestResultVo testResultVo = JSONUtil.parseObject(result.toString(), TestResultVo.class);
            String outputMD5 = MD5Utils.getMD5Checksum(testParam.getOutputPath());
            String stdMD5 = MD5Utils.GetStringMD5(stdout.getBytes());
            if(!outputMD5.equals(stdMD5)){
                  testResultVo.setResult(6);
            }
            return testResultVo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteTempFile(String fileName){
        File exe = new File(String.format("%s/file/bin/%s", pwd, fileName));
        File out = new File(String.format("%s/file/out/%s.out", pwd, fileName));
        File in = new File(String.format("%s/file/in/%s.in", pwd, fileName));
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
        }
        return pwd;
    }
    private static void checkFolderExists(){
        File srcDir = new File(getPWD() + "/file/src");
        srcDir.mkdirs();
        File outDir = new File(getPWD() + "/file/out");
        outDir.mkdirs();
        File inDir = new File(getPWD() + "/file/in");
        inDir.mkdirs();
        File errorDir = new File(getPWD() + "/file/error");
        errorDir.mkdirs();
        File binDir = new File(getPWD() + "/file/bin");
        binDir.mkdirs();
    }
}
