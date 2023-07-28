package com.Ankit.Recipe.Management.System.API.repository;

import com.Ankit.Recipe.Management.System.API.model.Post;
import com.Ankit.Recipe.Management.System.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
}
