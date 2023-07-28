package com.Ankit.Recipe.Management.System.API.model.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetUserDto {
    private Long userId;
    private String userName;
}
