package com.example.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;
import com.example.demo.model.MyAccount;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MyAccount myAccount;

	@GetMapping("/profile")
	public String showMyPage(Model model) {

		User user = userRepository.findById(myAccount.getId()).get();
		model.addAttribute("user", user);

		return "myprofile";
	}

	@PostMapping("/profile")
	public String editProfile(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "confirmPass") String confirmPass,
			@RequestParam(name = "bio") String bio,
			@RequestParam(name = "image") MultipartFile image,
			Model model) {

		User user = userRepository.findById(myAccount.getId()).get();
		user.setUsername(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setBio(bio);
		myAccount.setName(name);

		try {
			String uploadDir = new File("src/main/resources/static/images/icon").getAbsolutePath();
			Files.createDirectories(Paths.get(uploadDir));

			// 画像ファイルが指定されているか確認
			String savedFilename = user.getImages(); // 初期値：今の画像
			if (image != null && !image.isEmpty()) {
				String originalFilename = image.getOriginalFilename();
				String baseName = originalFilename.contains(".")
						? originalFilename.substring(0, originalFilename.lastIndexOf('.'))
						: originalFilename;
				String extension = originalFilename.contains(".")
						? originalFilename.substring(originalFilename.lastIndexOf('.'))
						: "";

				Path filePath = Paths.get(uploadDir, originalFilename);
				int count = 1;
				while (Files.exists(filePath)) {
					filePath = Paths.get(uploadDir, baseName + "_" + count + extension);
					count++;
				}

				image.transferTo(filePath.toFile());
				savedFilename = filePath.getFileName().toString(); // 保存ファイル名を更新
			}

			user.setImages(savedFilename);
			myAccount.setImages(savedFilename);
			userRepository.save(user);

			return "redirect:/top";

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

}
