package com.dange.tanmay.repository;

import com.dange.tanmay.dao.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface  UserRepository  extends CrudRepository<User, Integer> {
    @Modifying
    @Transactional
    @Query("update User u set u.forceEnabled = ?1 where u.username = ?2")
    int forceEnableMFA(@Param("forceEnabled") boolean forceEnabled, @Param("username") String username);
}
