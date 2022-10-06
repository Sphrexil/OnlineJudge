package com.oj.feign;

import com.oj.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("reception")
public interface ArticleUseFeignService {
    @GetMapping("/{id}")
    ResponseResult getUserById(@PathVariable("id") Long userId);
}
