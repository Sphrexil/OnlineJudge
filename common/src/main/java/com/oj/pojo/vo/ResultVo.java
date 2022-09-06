package com.oj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * description: ResultVo
 * date: 2022/9/6 14:15
 * author: zhenyu
 * version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo {
    public void setStatus(String status){
        if("".equals(this.status)){
            this.status = status;
        }else{
            this.status = "VARIOUS_ERROR_OCCUR";
        }
    }
    public void setMemoryUsage(int memoryUsage){
        this.memoryUsage = Math.max(memoryUsage, this.memoryUsage);
    }
    public void setTimeUsage(int timeUsage){
        this.timeUsage = Math.max(timeUsage, this.timeUsage);
    }
    private Integer memoryUsage = 0;

    private Integer timeUsage = 0;

    private String compileError;

    private String status = "";
}
