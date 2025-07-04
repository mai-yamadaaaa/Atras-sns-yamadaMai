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
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MyAccount myAccount;

	@GetMapping({ "/", "/login", "/logout" })
	public String loginPage() {
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

		return "redirect:/topPage";
	}

	@GetMapping("/addAccount")
	public String createAccountPage() {
		return "createAccount";
	}

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
			}
			if (email.isEmpty()) {
				model.addAttribute("emailMessage", "メールアドレスを入力してください");
			}
			if (password.isEmpty() || confirmPass.isEmpty()) {
				model.addAttribute("passMessage", "パスワードを入力してください");
			}
			return "createAccount";
		}

		if (!password.equals(confirmPass)) {
			model.addAttribute("passMessage", "パスワードが一致しません");
			return "createAccount";
		}

		User user = new User();

		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);

		return "redirect:/login";
	}
}
