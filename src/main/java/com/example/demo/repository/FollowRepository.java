package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Follow;
import com.example.demo.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

	Follow findByUser(User user);

}
