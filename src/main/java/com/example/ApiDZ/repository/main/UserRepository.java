package com.example.ApiDZ.repository.main;

import com.example.ApiDZ.domain.main.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional("mainTransactionManager")
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByUsernameIgnoreCase(String username);
}
