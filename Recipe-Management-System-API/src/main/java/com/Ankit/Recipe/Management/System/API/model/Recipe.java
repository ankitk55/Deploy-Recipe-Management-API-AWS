package com.Ankit.Recipe.Management.System.API.model;

import com.Ankit.Recipe.Management.System.API.service.RecipeService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;
    @NotBlank
    private String recipeName;

    @NotBlank
    private String recipeIngredients;

    @NotBlank
    private String recipeInstructions;

    @ManyToOne
    @JoinColumn(name = "fk_userId")
    private User user;
}
