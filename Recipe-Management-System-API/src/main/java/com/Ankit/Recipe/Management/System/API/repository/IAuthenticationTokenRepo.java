package com.Ankit.Recipe.Management.System.API.repository;

import com.Ankit.Recipe.Management.System.API.model.AuthenticationToken;
import com.Ankit.Recipe.Management.System.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationTokenRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByUser(User user);

    AuthenticationToken findFirstByTokenValue(String authTokenValue);



}
