package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "follows")
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "following_id", nullable = false)
	private Long followingId;

	@Column(name = "followed_id", nullable = false)
	private Long followedId;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt = LocalDateTime.now();

	//getter

	public Long getId() {
		return id;
	}

	public Long getFollowingId() {
		return followingId;
	}

	public Long getFollowedId() {
		return followedId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	//setter
	public void setId(Long id) {
		this.id = id;
	}

	public void setFollowingId(Long followingId) {
		this.followingId = followingId;
	}

	public void setFollowedId(Long followedId) {
		this.followedId = followedId;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
