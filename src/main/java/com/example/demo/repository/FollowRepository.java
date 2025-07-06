package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	List<Follow> findByFollowId(Long id);

	List<Follow> findByFollowerId(Long id);
}
