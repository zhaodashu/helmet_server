package com.zmm.helmet.service;

import com.zmm.helmet.common.UserList;
import com.zmm.helmet.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User login(User user);
    User getByName(String name);
    List<User> findAll();
    List<Object> getByQuery(String access_level, String query);
    <S extends User> boolean addUser(S u);
    User getById(Long id);
    public <S extends User> S save(S user);
    void deleteById(Long id);
}
