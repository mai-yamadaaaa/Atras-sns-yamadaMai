package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.MyAccount;
import com.example.demo.repository.UserRepository;

@Controller
public class MypageController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MyAccount myAccount;

	@GetMapping("/myPage")
	public String showMyPage(Model model) {

		User user = userRepository.findById(myAccount.getId()).get();
		model.addAttribute("user", user);

		return "myPage";
	}

	@PostMapping("/myPage")
	public String editMyPage(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "confirmPass") String confirmPass,
			@RequestParam(name = "bio") String bio,
			@RequestParam(name = "image") String image,
			Model model) {

		//バリデーションはあとで
		User user = userRepository.findById(myAccount.getId()).get();
		user.setUsername(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setBio(bio);
		user.setImages(image);

		return "redirect:/myPage";
	}

}
