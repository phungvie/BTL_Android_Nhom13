package viet.spring.SonicServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import viet.spring.SonicServer.entity.Comment;

public interface CommentRepository  extends JpaRepository<Comment, Integer>{

}
