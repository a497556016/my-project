package com.heshaowei.myproj.im.server.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.im.server.dto.LoginUserDTO;
import com.heshaowei.myproj.im.server.model.User;
import com.heshaowei.myproj.im.server.repository.UserRepository;
import com.heshaowei.myproj.im.server.utils.GsonUtil;
import com.heshaowei.myproj.im.server.utils.JwtUtils;
import com.heshaowei.myproj.im.server.utils.LoginUserUtil;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public Result<LoginUserDTO> login(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User matched = this.userRepository.findOne(Example.of(user)).orElse(null);
        if(null == matched){
            return Result.error("用户不存在！");
        }

        return Result.success(createLoginUser(matched));
    }

    private LoginUserDTO createLoginUser(User user){
        LoginUserDTO loginUser = new LoginUserDTO();
        loginUser.setId(user.getId().toString());
        loginUser.setCreateTime(user.getId().getDate());
        loginUser.setUsername(user.getUsername());
        loginUser.setNickname(user.getNickname());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setPhone(user.getPhone());
        Map<String, Object> map = Maps.newHashMap();
        map.put("loginUser", loginUser);
        String token = JwtUtils.create(loginUser.getUsername(), map);
        loginUser.setToken(token);
        return loginUser;
    }

    @PostMapping("/register")
    public Result<LoginUserDTO> register(@RequestBody User user){
        this.userRepository.save(user);
        return Result.success(createLoginUser(user));
    }

    @GetMapping("/list")
    public Result<List> list(HttpServletRequest request, String username, String nickname, String phone){
        User probe = new User();
        probe.setUsername(username);
        probe.setNickname(nickname);
        probe.setPhone(phone);
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        matcher.withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains());
        matcher.withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());

        List<User> users = this.userRepository.findAllByUsernameIsNot(LoginUserUtil.getUsername(request), Example.of(probe, matcher));
        /*List<User> users = this.userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                String token = request.getHeader("token");
                try {
                    String username = JwtUtils.parseJWT(token).getSubject();
                    predicates.add(criteriaBuilder.notEqual(root.get("username"), username));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                predicates.add(criteriaBuilder.like(root.get("username"), username));
                predicates.add(criteriaBuilder.like(root.get("nickname"), nickname));
                predicates.add(criteriaBuilder.like(root.get("phone"), phone));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });*/
        return Result.success(users);
    }
}
