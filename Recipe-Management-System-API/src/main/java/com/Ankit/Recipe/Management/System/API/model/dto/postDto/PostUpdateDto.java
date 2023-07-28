package com.Ankit.Recipe.Management.System.API.model.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostUpdateDto {
   private String postCaption;
    private Long recipeId;
}
