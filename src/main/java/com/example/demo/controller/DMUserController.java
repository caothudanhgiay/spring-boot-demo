package com.example.demo.controller;

import com.example.demo.Util.Constant;
import com.example.demo.enity.DMUser;
import com.example.demo.request.DMUserRequest;
import com.example.demo.service.DMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(Constant.API + "/user")
public class DMUserController  extends DMBaseController{

    @Autowired
    DMUserService dmUserService;

    @GetMapping("/{id}")
    public DMUser getUserById(@PathVariable Long id){
        return dmUserService.getUserById(id);
    }

    @GetMapping("/name")
    public List<DMUser> getUsersByName(@RequestParam String name){
        return dmUserService.getUsersByName(name);
    }

    @PostMapping("/search")
    public List<DMUser> searchUsers(@RequestBody DMUserRequest request){
        return dmUserService.getUsersByNameAndEmail(request.getName(), request.getEmail());
    }

    @GetMapping("/oauth2")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal){
        return principal.getAttributes();
    }
}
