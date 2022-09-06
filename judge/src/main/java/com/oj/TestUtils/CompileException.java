package com.oj.TestUtils;

import lombok.Data;

/**
 * @author zzy
 */
public class CompileException extends Exception{
    CompileException(String e){
        super(e);
    }
}
