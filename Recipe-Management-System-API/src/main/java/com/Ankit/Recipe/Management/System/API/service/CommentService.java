package com.Ankit.Recipe.Management.System.API.service;

import com.Ankit.Recipe.Management.System.API.model.Comment;
import com.Ankit.Recipe.Management.System.API.model.Post;
import com.Ankit.Recipe.Management.System.API.model.User;
import com.Ankit.Recipe.Management.System.API.model.dto.commentDto.AddCommentDto;
import com.Ankit.Recipe.Management.System.API.model.dto.commentDto.GetCommentDto;
import com.Ankit.Recipe.Management.System.API.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    ICommentRepo commentRepo;
    @Autowired
    PostService postService;


    public String postComment(AddCommentDto addCommentDto, User user, Post post) {
        Comment comment = new Comment();
        comment.setCommenter(user);
        comment.setPost(post);
        comment.setCommentCreationTimeStamp(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        comment.setCommentContent(addCommentDto.getCommentContent());
        commentRepo.save(comment);
        return "comment Added ..";
    }

    public String deleteCommentById(User user, Long commentId) {
        Comment comment =commentRepo.findById(commentId).orElse(null);
        if(comment==null || !comment.getCommenter().equals(user)){
            return "invalid comment id ..";
        }
        commentRepo.deleteById(commentId);
        return "comment deleted .. ";
    }

    public ResponseEntity<List<GetCommentDto>> allCommentsByPostId(User user, Long postId) {
        Post post= postService.findPostById(postId);
        if(post==null){
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
        List<Comment>commentList =commentRepo.findByPost( post);
        List<GetCommentDto>dtoCommentList=new ArrayList<>();

        for(Comment comment:commentList){
            GetCommentDto commentDto =new GetCommentDto(comment.getCommentId(),comment.getCommentContent(),
                    comment.getCommentCreationTimeStamp(),comment.getCommenter().getUserId(),comment.getCommenter().getUserName());
            dtoCommentList.add(commentDto);
        }
        return ResponseEntity.ok(dtoCommentList);
    }

    public void deleteAllCommentByUser(User commenter) {
        List<Comment>commentList=commentRepo.findByCommenter(commenter);
        commentRepo.deleteAll(commentList);
    }
}
