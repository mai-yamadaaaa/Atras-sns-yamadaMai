package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Follow;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.model.MyAccount;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class PostController {

	@Autowired
	MyAccount myAccount;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	FollowRepository followRepository;

	@GetMapping("/top")
	public String showTopPage(Model model) {
		User user = userRepository.findById(myAccount.getId()).get();
		List<Post> allPost = postRepository.findAll();

		model.addAttribute("user", user);
		model.addAttribute("posts", allPost);

		return "top";
	}

	@PostMapping("/post")
	public String createPost(
			@RequestParam(name = "post") String post, Model model) {

		User user = userRepository.findById(myAccount.getId()).get();

		Post postAdd = new Post();
		postAdd.setUser(user);
		postAdd.setPost(post);

		postRepository.save(postAdd);
		model.addAttribute("user", user);

		return "redirect:/top";
	}

	@PostMapping("/post/delete")
	public String deletePost(@RequestParam(name = "id") Long id) {
		postRepository.deleteById(id);

		return "redirect:/top";
	}

	@PostMapping("/post/edit")
	public String editPost(@RequestParam(name = "id") Long id,
			@RequestParam(name = "post") String post,
			Model model) {
		Post mypost = postRepository.findById(id).get();
		mypost.setPost(post);
		mypost.setUpdatedAt(LocalDateTime.now());
		postRepository.save(mypost);

		return "redirect:/top";
	}

	@GetMapping("/follow/posts")
	public String followPost(Model model) {
		User myUser = userRepository.findById(myAccount.getId()).get();
		List<Follow> follows = followRepository.findByFollowing(myUser);
		List<Post> posts = new ArrayList<>();

		for (Follow follow : follows) {
			posts.addAll(postRepository.findByUser(follow.getFollowed()));
		}

		model.addAttribute("follows", follows);
		model.addAttribute("posts", posts);
		return "follow_list";
	}

	@GetMapping("/follower/posts")
	public String followerPost(Model model) {
		User myUser = userRepository.findById(myAccount.getId()).get();
		List<Follow> followers = followRepository.findByFollowed(myUser);
		List<Post> posts = new ArrayList<>();

		for (Follow follower : followers) {
			posts.addAll(postRepository.findByUser(follower.getFollowing()));
		}

		model.addAttribute("followers", followers);
		model.addAttribute("posts", posts);

		return "follower_list";
	}

}
