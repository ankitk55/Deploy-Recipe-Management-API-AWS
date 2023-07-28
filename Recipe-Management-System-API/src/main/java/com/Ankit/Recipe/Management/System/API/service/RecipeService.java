package com.Ankit.Recipe.Management.System.API.service;

import com.Ankit.Recipe.Management.System.API.model.Recipe;
import com.Ankit.Recipe.Management.System.API.model.User;
import com.Ankit.Recipe.Management.System.API.model.dto.recipeDto.GetRecipeDto;
import com.Ankit.Recipe.Management.System.API.model.dto.recipeDto.RecipeDto;
import com.Ankit.Recipe.Management.System.API.repository.IRecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    @Autowired
    IRecipeRepo recipeRepo;

    public String addRecipe(RecipeDto recipeDto, User user) {
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeDto.getRecipeName());
        recipe.setRecipeIngredients(recipeDto.getRecipeIngredients());
        recipe.setRecipeInstructions(recipeDto.getRecipeInstructions());
        recipe.setUser(user);
        recipeRepo.save(recipe);
        return "recipe Added..";
    }

    public Recipe findById(Long recipeId) {
        return recipeRepo.findById(recipeId).orElse(null);
    }

    public String updateRecipe(User user, Long recipeId, RecipeDto recipeDto) {
        Recipe recipe =recipeRepo.findById(recipeId).orElse(null);
        if(recipe==null){
            return "invalid recipe Id..";
        }
       User repUser = recipe.getUser();
        if(!repUser.equals(user)){
            return "invalid recipe Id..";
        }
        if(recipeDto.getRecipeName()!=null)
        recipe.setRecipeName(recipeDto.getRecipeName());

        if(recipeDto.getRecipeIngredients()!=null)
            recipe.setRecipeIngredients(recipeDto.getRecipeIngredients());

        if(recipeDto.getRecipeInstructions()!=null)
            recipe.setRecipeInstructions(recipeDto.getRecipeInstructions());

        recipeRepo.save(recipe);
        return "recipe updated";
    }

    public String deleteRecipeById(User user, Long recipeId) {
        Recipe recipe = recipeRepo.findById(recipeId).orElse(null);
        if(recipe==null || !recipe.getUser().equals(user)){
            return "Invalid recipe id";
        }
        recipeRepo.deleteById(recipeId);
        return "recipe deleted for id : "+recipeId;
    }

    public ResponseEntity<List<GetRecipeDto>> allRecipes(User user) {
        List<Recipe>recipeList =recipeRepo.findByUser(user);
        List<GetRecipeDto>allRecipeList =new ArrayList<>();
        for(Recipe recipe:recipeList){
            GetRecipeDto recipeDto = new GetRecipeDto(recipe.getRecipeId(),recipe.getRecipeName(),recipe.getRecipeIngredients(),
                    recipe.getRecipeInstructions());

            allRecipeList.add(recipeDto);
        }
        return ResponseEntity.ok(allRecipeList);
    }

    public void deleteAllRecipeByUser(User user) {
        List<Recipe>recipeList =recipeRepo.findByUser(user);
        recipeRepo.deleteAll(recipeList);
    }
}
