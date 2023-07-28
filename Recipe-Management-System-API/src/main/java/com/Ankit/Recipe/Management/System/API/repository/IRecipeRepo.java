package com.Ankit.Recipe.Management.System.API.repository;

import com.Ankit.Recipe.Management.System.API.model.Recipe;
import com.Ankit.Recipe.Management.System.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRecipeRepo extends JpaRepository<Recipe,Long> {
    List<Recipe> findByUser(User user);
}
