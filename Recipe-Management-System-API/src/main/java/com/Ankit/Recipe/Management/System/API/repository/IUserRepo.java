package com.Ankit.Recipe.Management.System.API.repository;

import com.Ankit.Recipe.Management.System.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User,Long> {
    User findFirstByUserEmail(String newEmail);
}
