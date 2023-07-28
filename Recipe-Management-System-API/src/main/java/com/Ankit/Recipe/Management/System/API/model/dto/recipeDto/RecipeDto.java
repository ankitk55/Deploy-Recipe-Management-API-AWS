package com.Ankit.Recipe.Management.System.API.model.dto.recipeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeDto {
    private String recipeName;
    private String recipeIngredients;
    private String recipeInstructions;
}
