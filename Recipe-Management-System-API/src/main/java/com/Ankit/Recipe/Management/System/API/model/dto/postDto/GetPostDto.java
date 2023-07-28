package com.Ankit.Recipe.Management.System.API.model.dto.postDto;

import com.Ankit.Recipe.Management.System.API.model.dto.recipeDto.GetRecipeDto;
import com.Ankit.Recipe.Management.System.API.model.dto.userDto.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetPostDto {
    private Long postId;
    private String postCaption;
    private LocalDateTime postCreationTimestamp;
    private LocalDateTime postUpdationTimeStamp;
private  GetUserDto postBy;
private GetRecipeDto recipe;
}
