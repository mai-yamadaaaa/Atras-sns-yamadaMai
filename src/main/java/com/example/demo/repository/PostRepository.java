package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Post;
import com.example.demo.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUser(User user);

}
