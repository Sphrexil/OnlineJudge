package com.oj.controller;


import com.oj.service.CategoryService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){

        return categoryService.getCategoryList();

    }

}
