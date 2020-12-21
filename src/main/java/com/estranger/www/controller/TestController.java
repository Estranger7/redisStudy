package com.estranger.www.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by：Estranger
 * Description：测试controller
 * Date：2020/9/11 15:46
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/index")
    public void index(){
        System.out.println(123);
    }
}
