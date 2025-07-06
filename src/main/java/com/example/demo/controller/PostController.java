package com.example.demo.controller;

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

	@GetMapping("/folow/post")
	public String followPost(Model model) {
		Follow followList = followRepository.findById(myAccount.getId()).get();
		List<Post> posts = postRepository.findByUserId(followList.getFollowerId());

		model.addAttribute("follows", followList.getFollowId());
		model.addAttribute("posts", posts);
		return "followPost";
	}

	@GetMapping("follower/post")
	public String followerPost(Model model) {
		Follow followerList = followRepository.findById(myAccount.getId()).get();
		List<Post> posts = postRepository.findByUserId(followerList.getFollowerId());

		model.addAttribute("followerList", followerList);
		model.addAttribute("posts", posts);
		return "followerPost";
	}

	@GetMapping("user/search")
	public String userSearch(Model model) {

		return "userSearch";
	}

}
