package com.example.demo.service;

import com.example.demo.enity.DMUser;

import java.util.List;

public interface DMUserService {

    public DMUser getUserById(Long id);

    public List<DMUser> getUsersByName(String name);

    public List<DMUser> getUsersByNameAndEmail(String name, String email);

}
