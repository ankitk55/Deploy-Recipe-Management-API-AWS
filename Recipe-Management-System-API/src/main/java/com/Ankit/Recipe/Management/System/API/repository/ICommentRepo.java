package com.Ankit.Recipe.Management.System.API.repository;

import com.Ankit.Recipe.Management.System.API.model.Comment;
import com.Ankit.Recipe.Management.System.API.model.Post;
import com.Ankit.Recipe.Management.System.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByCommenter(User commenter);
}
