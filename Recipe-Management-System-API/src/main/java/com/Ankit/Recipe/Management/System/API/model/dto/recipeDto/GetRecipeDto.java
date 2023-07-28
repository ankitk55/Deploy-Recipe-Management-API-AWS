package com.Ankit.Recipe.Management.System.API.model.dto.recipeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetRecipeDto {
    private Long recipeId;
    private String recipeName;
    private String recipeIngredients;
    private String recipeInstructions;

}
