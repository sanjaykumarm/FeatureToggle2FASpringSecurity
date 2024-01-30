package com.dange.tanmay.repository;

import com.dange.tanmay.dao.User;
import org.springframework.data.repository.CrudRepository;

public interface  UserRepository  extends CrudRepository<User, Integer> {
}
