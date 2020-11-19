package com.zmm.helmet.service.impl;

import com.zmm.helmet.common.UserList;
import com.zmm.helmet.entity.User;
import com.zmm.helmet.repository.UserRepository;
import com.zmm.helmet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(User user) {
        User userDB = userRepository.findByUsername(user.getUsername());
        if (!ObjectUtils.isEmpty(userDB)){
            if (userDB.getPassword().equals(user.getPassword())) {
                return userDB;
            }else {
                throw new RuntimeException("密码输入不正确");
            }
        }else {
            throw new RuntimeException("用户名输入错误");
        }
    }

    @Override
    public User getByName(String name) {
        User userDB = userRepository.findByUsername(name);
        return userDB;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Object> getByQuery(String access_level, String query) {
        return userRepository.findByUsernameAndAccess_level(access_level, query);
    }

    @Override
    public <S extends User> boolean addUser(S u) {
        return (userRepository.save(u).getUsername() != null);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findByUserid(id);
    }

    @Override
    public <S extends User> S save(S user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        User userDB = userRepository.findByUserid(id);
        userRepository.delete(userDB);
    }


}
