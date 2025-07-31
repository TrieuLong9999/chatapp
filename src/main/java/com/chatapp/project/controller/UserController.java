package com.chatapp.project.controller;

import com.chatapp.project.entity.UserEntity;
import com.chatapp.project.form.response.user.UserView;
import com.chatapp.project.service.UserService;
import com.chatapp.project.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("list-user")
    public BaseResponse<?> lístUser( @RequestParam(required = false, defaultValue = "") String username,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size){
        Page<UserView> allUser = userService.findAllUser(username, page, size);
        return BaseResponse.success(allUser);
    }


}
