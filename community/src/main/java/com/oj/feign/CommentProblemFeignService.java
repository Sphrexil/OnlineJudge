package com.oj.feign;

import com.oj.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

@FeignClient("problem")
public interface CommentProblemFeignService {
    @GetMapping("/problem/info/{id}")
    ResponseResult getProblemById(@PathVariable("id") @NotNull Long id);
}
