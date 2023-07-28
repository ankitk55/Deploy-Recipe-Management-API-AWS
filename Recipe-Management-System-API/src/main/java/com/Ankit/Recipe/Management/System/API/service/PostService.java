package com.Ankit.Recipe.Management.System.API.service;

import com.Ankit.Recipe.Management.System.API.model.Post;
import com.Ankit.Recipe.Management.System.API.model.Recipe;
import com.Ankit.Recipe.Management.System.API.model.User;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.AddPostDto;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.GetPostDto;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.PostUpdateDto;
import com.Ankit.Recipe.Management.System.API.model.dto.recipeDto.GetRecipeDto;
import com.Ankit.Recipe.Management.System.API.model.dto.userDto.GetUserDto;
import com.Ankit.Recipe.Management.System.API.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    IPostRepo postRepo;
    @Autowired
    RecipeService recipeService;

    public String postRecipe(AddPostDto addPostDto, User user) {
        Post post =new Post();
        Long recipeId = addPostDto.getRecipeId();
        Recipe recipe = recipeService.findById(recipeId);
        if(recipe==null){
            return "add valid recipe";
        }
        User repUser =recipe.getUser();
        if(!repUser.equals(user)){
            return "add valid recipe";
        }
        post.setPostCaption(addPostDto.getPostCaption());
        post.setRecipe(recipe);
        post.setUser(user);
        post.setPostCreationTimestamp(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        postRepo.save(post);
    return "post uploaded ..";
    }

    public Post findPostById(Long postId) {
        return postRepo.findById(postId).orElse(null);
    }

    public ResponseEntity<List<GetPostDto>> allPost(User user) {
        List<Post>postList =postRepo.findByUser(user);
        List<GetPostDto>postDtoList = new ArrayList<>();
        for (Post post:postList){
            GetPostDto getPostDto =new GetPostDto(post.getPostId(),post.getPostCaption(),post.getPostCreationTimestamp(),
                    post.getPostUpdationTimeStamp(),new GetUserDto(post.getUser().getUserId(),post.getUser().getUserName()),
                    new GetRecipeDto(post.getRecipe().getRecipeId(),post.getRecipe().getRecipeName(),post.getRecipe().getRecipeIngredients(),
                            post.getRecipe().getRecipeInstructions()));
            postDtoList.add(getPostDto);
        }
        return ResponseEntity.ok(postDtoList);
    }

    public String updatePostById(Long postId, PostUpdateDto postUpdateDto, User user) {
        Post post = postRepo.findById(postId).orElse(null);
        if(post==null || !post.getUser().equals(user)){
            return "invalid post id ..";
        }
        Recipe recipe = recipeService.findById(postUpdateDto.getRecipeId());

        if(recipe==null || !recipe.getUser().equals(user)){
            return "invalid recipe Id..";
        }
        post.setPostCaption(postUpdateDto.getPostCaption());
        post.setRecipe(recipe);
        post.setPostUpdationTimeStamp(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        postRepo.save(post);
        return "post updated..";
    }

    public String deletePostById(User user, Long postId) {
        Post post = postRepo.findById(postId).orElse(null);
        if(post==null || !post.getUser().equals(user)){
            return "invalid post id ..";
        }
        postRepo.deleteById(postId);
        return "post deleted ..";
    }

    public ResponseEntity<List<GetPostDto>> getAllPosts() {
        List<Post>postList =postRepo.findAll();
        List<GetPostDto>postDtoList = new ArrayList<>();
        for (Post post:postList){
            GetPostDto getPostDto =new GetPostDto(post.getPostId(),post.getPostCaption(),post.getPostCreationTimestamp(),
                    post.getPostUpdationTimeStamp(),new GetUserDto(post.getUser().getUserId(),post.getUser().getUserName()),
                    new GetRecipeDto(post.getRecipe().getRecipeId(),post.getRecipe().getRecipeName(),post.getRecipe().getRecipeIngredients(),
                            post.getRecipe().getRecipeInstructions()));
            postDtoList.add(getPostDto);
        }
        return ResponseEntity.ok(postDtoList);
    }

    public ResponseEntity<GetPostDto> getPostById(Long postId) {
        Post post =postRepo.findById(postId).orElse(null);
        if(post==null){
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
        GetPostDto postDto =new GetPostDto(post.getPostId(),post.getPostCaption(),post.getPostCreationTimestamp(),
                post.getPostUpdationTimeStamp(),new GetUserDto(post.getUser().getUserId(),post.getUser().getUserName()),
                new GetRecipeDto(post.getRecipe().getRecipeId(),post.getRecipe().getRecipeName(),post.getRecipe().getRecipeIngredients(),
                        post.getRecipe().getRecipeInstructions()));
        return ResponseEntity.ok(postDto);
    }

    public void deleteAllPostByUser(User user) {
        List<Post>postList =postRepo.findByUser(user);
        List<Long>postIds =new ArrayList<>();
        for(Post post :postList){
            postIds.add(post.getPostId());
        }
        postRepo.deleteAllById(postIds);
    }


}
