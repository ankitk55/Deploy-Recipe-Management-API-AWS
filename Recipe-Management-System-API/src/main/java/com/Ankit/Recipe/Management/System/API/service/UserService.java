package com.Ankit.Recipe.Management.System.API.service;

import com.Ankit.Recipe.Management.System.API.model.*;
import com.Ankit.Recipe.Management.System.API.model.dto.SignInInput;
import com.Ankit.Recipe.Management.System.API.model.dto.SignUpOutput;
import com.Ankit.Recipe.Management.System.API.model.dto.commentDto.AddCommentDto;
import com.Ankit.Recipe.Management.System.API.model.dto.commentDto.GetCommentDto;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.AddPostDto;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.GetPostDto;
import com.Ankit.Recipe.Management.System.API.model.dto.postDto.PostUpdateDto;
import com.Ankit.Recipe.Management.System.API.model.dto.recipeDto.GetRecipeDto;
import com.Ankit.Recipe.Management.System.API.model.dto.recipeDto.RecipeDto;
import com.Ankit.Recipe.Management.System.API.repository.IUserRepo;
import com.Ankit.Recipe.Management.System.API.service.utility.emailUtility.EmailHandler;
import com.Ankit.Recipe.Management.System.API.service.utility.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RecipeService recipeService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if (newEmail == null) {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //check if this User email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if (existingUser != null) {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            user.setUserRegisteredTimeStamp(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        } catch (Exception e) {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

    }

    public String signInUser(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if (signInEmail == null) {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this User email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if (existingUser == null) {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if (existingUser.getUserPassword().equals(encryptedPassword)) {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.createToken(authToken);

                EmailHandler.sendEmail(signInEmail, "Token for Verify Identity !!!", authToken.getTokenValue());
                return "Token sent to your email";
            } else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        } catch (Exception e) {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public String addRecipe(String email, String token, RecipeDto recipeDto) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return recipeService.addRecipe(recipeDto,user);
        }
        return "invalid credentials";
    }

    public String postRecipe(String email, String token, AddPostDto addPostDto) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.postRecipe(addPostDto,user);
        }
        return "invalid credentials";
    }

    public String postComment(String email, String token, AddCommentDto addCommentDto, Long postId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            Post post =postService.findPostById(postId);
            if(post==null){
                return "invalid post Id ..";
            }
            return commentService.postComment(addCommentDto,user, post);
        }
        return "invalid credentials";
    }

    public String updateRecipe(String email, String token, Long recipeId, RecipeDto recipeDto) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return recipeService.updateRecipe(user,recipeId,recipeDto);
        }
        return "invalid credentials";
    }

    public ResponseEntity<List<GetPostDto>> allPost(String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.allPost(user);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public String updatePostById(String email, String token, Long postId, PostUpdateDto postUpdateDto) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.updatePostById(postId,postUpdateDto,user);
        }
        return "invalid credentials";
    }

    public String deletePostById(String email, String token, Long postId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.deletePostById(user,postId);
        }
        return "invalid credentials";
    }

    public String deleteCommentById(String email, String token, Long commentId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return commentService.deleteCommentById(user,commentId);
        }
        return "invalid credentials";
    }

    public ResponseEntity<List<GetCommentDto>> allCommentsByPostId(String email, String token, Long postId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return commentService.allCommentsByPostId(user,postId);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public ResponseEntity<List<GetPostDto>> getAllPosts(String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.getAllPosts();
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public String deleteRecipeById(String email, String token, Long recipeId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return recipeService.deleteRecipeById(user,recipeId);
        }
        return "invalid credentials";
    }

    public ResponseEntity<GetPostDto> getPostById(String email, String token, Long postId) {
        if(authenticationService.authenticate(email,token)){

            return postService.getPostById(postId);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public ResponseEntity<List<GetRecipeDto>> allRecipes(String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return recipeService.allRecipes(user);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public String updateUserEmail(String newEmail, String email, String token) {
        if (authenticationService.authenticate(email, token)) {
            User user = userRepo.findFirstByUserEmail(email);
            user.setUserEmail(newEmail);
            userRepo.save(user);
            return "emailId Successfully  changed ..";
        }
        return "invalid credentials";
    }

    public String updatePassword(String newPassword, String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            try {
                String encryptedPassword = PasswordEncrypter.encryptPassword(newPassword);
                user.setUserPassword(encryptedPassword);
                userRepo.save(user);
                return "password Successfully changed ..";
            }
            catch(Exception e){
                System.out.println("some error occurred during creating a Hash code of Password ");
            }
        }
        return "invalid credentials";
    }


        public String sigOutUser(String email, String token) {
        if (authenticationService.authenticate(email, token)) {

            User user = userRepo.findFirstByUserEmail(email);
            authenticationService.authTokenRepo.delete(authenticationService.authTokenRepo.findFirstByUser(user));
            return "User Signed out successfully";
        } else {
            return "Sign out not allowed for non authenticated user.";
        }
    }

    public String deleteUserAccount(String email, String token) {

            if(authenticationService.authenticate(email,token)){
                User user =userRepo.findFirstByUserEmail(email);
                // signOut the user
                authenticationService.authTokenRepo.delete(authenticationService.authTokenRepo.findFirstByUser(user));
                User commenter =user;
                commentService.deleteAllCommentByUser(commenter);
                postService.deleteAllPostByUser(user);
                recipeService.deleteAllRecipeByUser(user);

                userRepo.delete(user);
                return "Your Account deleted Successfully ..";
            }
            return "invalid credentials";

        }

}
