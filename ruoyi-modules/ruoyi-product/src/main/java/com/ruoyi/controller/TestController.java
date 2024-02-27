package com.ruoyi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/2/27 15:39
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("tt")
    public String test(){
        return "test";
    }
}
