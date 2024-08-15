package com.chuwa.service.impl;

import com.chuwa.config.JwtProperties;
import com.chuwa.dto.LoginFormDTO;
import com.chuwa.dto.SignupFormDTO;
import com.chuwa.exception.BadRequestException;
import com.chuwa.exception.ForbiddenException;
import com.chuwa.po.User;
import com.chuwa.repository.UserRepository;
import com.chuwa.service.UserService;
import com.chuwa.utils.JwtTool;
import com.chuwa.vo.UserLoginVO;


import com.chuwa.vo.UserSignupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTool jwtTool;
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    @Override
    public UserLoginVO login(LoginFormDTO loginDTO) {
        // 1.数据校验
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 2.根据用户名或手机号查询
        User user = userRepository.findByUsername(username);
        Assert.notNull(user, "用户名错误");

        // 3.校验是否禁用
        if (!user.isStatus()) {
            throw new ForbiddenException("用户被冻结");
        }

        // 4.校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("用户名或密码错误");
        }
        // 5.生成TOKEN
        String token = jwtTool.generateToken(user.getId().toString(), jwtProperties.getTokenTTL());
        // 6.封装VO返回
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setBalance(user.getBalance());
        vo.setToken(token);
        return vo;

    }

    @Override
    public UserSignupVO signup(SignupFormDTO signupFormDTO) {
        // 1. 检查用户名是否唯一
        String username = signupFormDTO.getUsername();
        User result = userRepository.findByUsername(username);
        if (result != null) {
            throw new BadRequestException("Username is taken, please try other names");
        }

        // 2. 设置密码并复制属性
        signupFormDTO.setPassword(passwordEncoder.encode(signupFormDTO.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(signupFormDTO, user);
        user.setBalance(100); // 设置初始余额
        user.setStatus(true); // 设置用户状态为激活
        user.setCreateTime(LocalDateTime.now()); // 设置创建时间
        user.setUpdateTime(LocalDateTime.now()); // 设置更新时间

        // 3. 保存到数据库
        User savedUser = userRepository.save(user);

        // 4. 返回用户注册成功的信息
        UserSignupVO userSignupVO = new UserSignupVO();
        userSignupVO.setUsername(savedUser.getUsername());
        userSignupVO.setBalance(savedUser.getBalance());
        userSignupVO.setCreateTime(savedUser.getCreateTime());

        return userSignupVO;
    }
}

