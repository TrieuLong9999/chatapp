package com.chatapp.project.controller;

import com.chatapp.project.utils.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client")
public class TestRoleController {
    @GetMapping("index")
    public BaseResponse<?> index(){
        return BaseResponse.success("abcd");
    }
}
