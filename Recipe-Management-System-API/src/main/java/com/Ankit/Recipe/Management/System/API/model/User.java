package com.Ankit.Recipe.Management.System.API.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank
    private String userName;
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.com$")
    private String userEmail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private String userPassword;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime userRegisteredTimeStamp;
}
