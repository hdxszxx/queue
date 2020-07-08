package com.queue.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zxx
 * @create 2020-06-30 16:03
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String init(){
        return "index";
    }
}