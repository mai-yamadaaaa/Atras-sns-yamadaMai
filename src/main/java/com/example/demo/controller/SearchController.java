package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.model.MyAccount;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class SearchController {

	@Autowired
	FollowRepository followRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MyAccount myAccount;

	@GetMapping("/seach")
	public String showSearchPage() {
		return "search";
	}

	@PostMapping("/seach")
	public String searchUser(@RequestParam(name = "keyword") String keyword,
			Model model) {

		User myUser = userRepository.findById(myAccount.getId()).get();
		List<User> searchUser = userRepository.findByUsernameContaining(keyword);

		List<Follow> followSearch = followRepository.findByFollowId(myUser.getId());

		return "redirect:/search";
	}

	//	@GetMapping("/others/{id}/user")
	//	public String showOtherAccount() {
	//		
	//	}

}
