package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class MyAccount {

	private Long id;
	private String name;
	private String images;

	//getter
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImages() {
		return images;
	}

	//setter
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImages(String images) {
		this.images = images;
	}

}
