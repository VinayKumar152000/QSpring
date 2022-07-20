package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bo.UserBo;
import com.example.demo.domain.User;
import com.example.demo.payload.UserPayload;
import com.example.demo.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService service;

	@GetMapping
	public List<UserBo> getAllUsers() {
		return service.getAllUsers();
	}

	@GetMapping("/{id}")
	public UserBo getSingleUser(@PathVariable(value = "id") int userId) {
		return service.getUserById(userId);
	}

	@PostMapping
	public UserBo createUser(@RequestBody UserPayload user) {
		return service.createUser(user);
	}

	@PutMapping("/{id}")
	public UserBo updateUser(@RequestBody UserPayload user, @PathVariable("id") int userId) {
		return service.updateUser(user, userId);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") int userId) {
		service.deleteUser(userId);
	}
}
