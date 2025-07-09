package com.example.demo.controller;

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

	@GetMapping("/topPage")
	public String showTopPage(Model model) {
		User user = userRepository.findById(myAccount.getId()).get();
		List<Post> allPost = postRepository.findAll();

		model.addAttribute("user", user);
		model.addAttribute("posts", allPost);

		return "topPage";
	}

	@PostMapping("/topPage")
	public String createPost(
			@RequestParam(name = "myPost") String myPost, Model model) {

		User user = userRepository.findById(myAccount.getId()).get();

		Post postAdd = new Post();
		postAdd.setUser(user);
		postAdd.setPost(myPost);

		postRepository.save(postAdd);

		model.addAttribute("user", user);

		return "redirect:topPage";
	}

	@GetMapping("/follow/post")
	public String followPost(Model model) {
		List<Follow> follows = followRepository.findByFollowId(myAccount.getId());
		List<Post> posts = new ArrayList<>();
		List<User> users = new ArrayList<>();

		for (Follow follow : follows) {
			// フォローしているユーザーの投稿をすべて追加
			posts.addAll(postRepository.findByUserId(follow.getFollowerId()));

			// ユーザー情報も取得してリストに追加
			userRepository.findById(follow.getFollowerId()).ifPresent(users::add);
		}

		model.addAttribute("follows", follows);
		model.addAttribute("posts", posts);
		model.addAttribute("users", users);
		return "followPost";
	}

	@GetMapping("follower/post")
	public String followerPost(Model model) {
		List<Follow> followers = followRepository.findByFollowerId(myAccount.getId());
		List<Post> posts = new ArrayList<>();
		List<User> users = new ArrayList<>();

		for (Follow follower : followers) {
			posts.addAll(postRepository.findByUserId(follower.getFollowId()));

			// ユーザー情報も取得してリストに追加
			userRepository.findById(follower.getFollowId()).ifPresent(users::add);
		}

		model.addAttribute("followers", followers);
		model.addAttribute("posts", posts);
		model.addAttribute("users", users);
		return "followerPost";
	}

	@GetMapping("user/search")
	public String userSearch(Model model) {

		return "userSearch";
	}

}
