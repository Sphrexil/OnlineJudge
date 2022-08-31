package com.oj.feign;

import com.oj.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("problem")
public interface ProblemFeignService {
    @GetMapping("/problem/info/{id}")
    ResponseResult getProblemById(@PathVariable("id") Long id);
}
