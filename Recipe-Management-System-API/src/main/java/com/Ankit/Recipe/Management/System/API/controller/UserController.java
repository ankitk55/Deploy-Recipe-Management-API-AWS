package com.Ankit.Recipe.Management.System.API.controller;

import com.Ankit.Recipe.Management.System.API.model.Comment;
import com.Ankit.Recipe.Management.System.API.model.Post;
import com.Ankit.Recipe.Management.System.API.model.Recipe;
import com.Ankit.Recipe.Management.System.API.model.User;
import com.Ankit.Recipe.Management.System.API.model.dto.SignInInput;
import com.Ankit.Recipe.Management.System.API.model.dto.SignUpOutput;
import com.Ankit.Recipe.Management.System.API.model.dto.commentDto.AddCommentDto;
import com.Ankit.Recipe.Management.System.API.model.dto.commentDto.GetCommentDto;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.AddPostDto;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.GetPostDto;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.PostUpdateDto;
import com.Ankit.Recipe.Management.System.API.model.dto.recipeDto.GetRecipeDto;
import com.Ankit.Recipe.Management.System.API.model.dto.recipeDto.RecipeDto;
import com.Ankit.Recipe.Management.System.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("user/signup")
    public SignUpOutput signUpUser(@RequestBody @Valid User user)
    {
        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }

    @PostMapping("recipe")
    public String addRecipe(@RequestParam @Valid String email, @RequestParam String token, @RequestBody RecipeDto recipeDto){
        return userService.addRecipe(email,token,recipeDto);
    }
    @PostMapping("post/recipe")
    public String postRecipe(@RequestParam @Valid String email, @RequestParam String token,@RequestBody AddPostDto addPostDto){
        return  userService.postRecipe(email,token,addPostDto);
    }
    @PostMapping("comment/post/{postId}")
    public String postComment(@RequestParam @Valid String email, @RequestParam String token, @RequestBody AddCommentDto addCommentDto, @PathVariable Long postId){
        return userService.postComment(email,token,addCommentDto,postId);
    }

    @PutMapping("recipe/{recipeId}")
    public String updateRecipe(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long recipeId, @RequestBody RecipeDto recipeDto){
        return userService.updateRecipe(email,token,recipeId,recipeDto);
    }
    @PutMapping("post/{postId}")
    public String updatePostById(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto){
        return userService.updatePostById(email,token,postId,postUpdateDto);
    }
    @GetMapping("my/posts")
    public ResponseEntity<List<GetPostDto>>allPost(@RequestParam @Valid String email, @RequestParam String token){
        return userService.allPost(email,token);
    }
    @DeleteMapping("post/{postId}")
    public String deletePostById(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long postId){
        return userService.deletePostById(email,token,postId);
    }
    @DeleteMapping("comment/{commentId}")
    public String deleteCommentById(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long commentId){
        return userService.deleteCommentById(email,token,commentId);
    }
    @GetMapping("comments/post/{postId}")
    public ResponseEntity<List<GetCommentDto>>allCommentsByPostId(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long postId){
        return userService.allCommentsByPostId(email,token,postId);
    }
    @GetMapping("All/posts")
    public ResponseEntity<List<GetPostDto>>getAllPosts(@RequestParam @Valid String email, @RequestParam String token){
        return userService.getAllPosts(email,token);
    }
    @DeleteMapping("recipe/{recipeId}")
    public String deleteRecipeById(@RequestParam @Valid String email, @RequestParam String token,@PathVariable Long recipeId){
        return userService.deleteRecipeById(email,token,recipeId);
    }
    @GetMapping("post/{postId}")
    public ResponseEntity<GetPostDto> getPostById(@RequestParam @Valid String email, @RequestParam String token,@PathVariable Long postId){
        return userService.getPostById(email,token,postId);
    }
    @GetMapping("my/recipes")
    public ResponseEntity<List<GetRecipeDto>>allRecipes(@RequestParam @Valid String email, @RequestParam String token){
        return userService.allRecipes(email,token);
    }

    @PutMapping("user/email/{newEmail}")
    public String updateUserEmail(@PathVariable String newEmail,@RequestParam @Valid String email, @RequestParam String token){
        return userService.updateUserEmail(newEmail,email,token);
    }

    @PutMapping("user/password/{newPassword}")
    public String updatePassword(@PathVariable  String newPassword,@RequestParam @Valid String email, @RequestParam String token){
        return userService.updatePassword(newPassword,email,token);}

    @DeleteMapping("user/signOut")
    public String sigOutUser(String email, String token) {
        return userService.sigOutUser(email, token);
    }

    @DeleteMapping("user/account")
    public String deleteUserAccount(@RequestParam @Valid String email,@RequestParam String token){

        return userService.deleteUserAccount(email,token);
    }
}
