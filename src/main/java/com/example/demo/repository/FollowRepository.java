package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Follow;
import com.example.demo.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	List<Follow> findByFollowing(User user);

	List<Follow> findByFollowed(User user);
}
