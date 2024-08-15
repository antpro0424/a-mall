package com.chuwa.controller;

import com.chuwa.dto.LoginFormDTO;
import com.chuwa.dto.SignupFormDTO;
import com.chuwa.service.UserService;
import com.chuwa.vo.UserLoginVO;
import com.chuwa.vo.UserSignupVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/account/user")
@Tag(name="User Controller", description = "APIs for user operations (login and pay).")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "User login api")
    @PostMapping("/login")
    public UserLoginVO userLogin(@RequestBody LoginFormDTO loginFormDTO) {
        return userService.login(loginFormDTO);
    }

    @Operation(summary = "user signup api")
    @PostMapping("/signup")
    public UserSignupVO signup(@RequestBody SignupFormDTO signupFormDTO) {return userService.signup(signupFormDTO);}
}