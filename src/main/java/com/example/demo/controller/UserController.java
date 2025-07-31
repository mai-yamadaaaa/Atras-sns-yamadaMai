package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.model.MyAccount;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;

	@Autowired
	MyAccount myAccount;

	@Autowired
	HttpSession session;

	@GetMapping({ "/login", "/logout" })
	public String loginPage() {
		session.removeAttribute("myAccount"); // ← セッション全体を消さず、ログイン情報だけクリア
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		if (email.isEmpty() || password.isEmpty()) {
			model.addAttribute("message", "メールアドレスとパスワードを入力してください");
			return "login";
		}
		User user = userRepository.findByEmailAndPassword(email, password);

		if (user == null) {
			model.addAttribute("message", "一致するアカウントが見つかりませんでした");
			return "login";
		}

		myAccount.setId(user.getId());
		myAccount.setName(user.getUsername());
		myAccount.setImages(user.getImages());

		return "redirect:/top";
	}

	@GetMapping("/user/create")
	public String createAccountPage() {
		return "register";
	}

	@PostMapping("/user/create")
	public String createAccount(
			@RequestParam(name = "username", defaultValue = "") String username,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "confirmPass", defaultValue = "") String confirmPass,
			Model model) {

		if (username.isEmpty() || email.isEmpty() ||
				password.isEmpty() || confirmPass.isEmpty()) {

			if (username.isEmpty()) {
				model.addAttribute("nameMassage", "ユーザ名を入力してください");
			} else if (email.isEmpty()) {
				model.addAttribute("emailMessage", "メールアドレスを入力してください");
			} else if (password.isEmpty() || confirmPass.isEmpty()) {
				model.addAttribute("passMessage", "パスワードを入力してください");
			}
			return "register";
		}

		if (!password.equals(confirmPass)) {
			model.addAttribute("confirmPassMessage", "パスワードが一致しません");
			return "register";
		}

		User user = new User();

		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);

		myAccount.setName(user.getUsername());
		userRepository.save(user);

		return "redirect:/user/create/conplete";
	}

	@GetMapping("/user/create/conplete")
	public String conpleteCreate() {
		return "register_conplete";
	}

	@GetMapping("/user/{id}/detail")
	public String showUserDetail(@PathVariable(name = "id") Long id, Model model) {
		User user = userRepository.findById(id).get();
		List<Post> posts = postRepository.findByUser(user);

		model.addAttribute("posts", posts);
		model.addAttribute("user", user);
		return "user_detail";
	}

	@GetMapping("/user/search")
	public String userSearchPage(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
		List<User> users = new ArrayList<>();
		if (keyword != null && !keyword.isEmpty()) {
			users = userRepository.findByUsernameContaining(keyword);
		} else {
			users = userRepository.findByIdNot(myAccount.getId());
		}
		model.addAttribute("keyword", keyword);
		model.addAttribute("users", users);
		return "user_search";
	}

}
