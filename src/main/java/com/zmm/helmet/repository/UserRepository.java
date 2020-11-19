package com.zmm.helmet.repository;

import com.zmm.helmet.common.UserList;
import com.zmm.helmet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT u.user_id, u.username, u.access_level, u.create_time FROM user_model u "+
            "WHERE u.access_level = ?1 AND u.username LIKE %?2% ")
    List<Object> findByUsernameAndAccess_level(String access_level, String query);

    User findByUserid(Long id);
    <S extends User> S save(S user);
}
