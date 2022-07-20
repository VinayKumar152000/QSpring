package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.bo.RoleBo;
import com.example.demo.bo.SubjectBo;
import com.example.demo.bo.UserBo;
import com.example.demo.domain.Role;
import com.example.demo.domain.Subject;
import com.example.demo.domain.User;
import com.example.demo.exceptions.InvalidInfoException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payload.UserPayload;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private RoleRepository rolerepo;

	@Autowired
	private ModelMapper modelMapper;

	public List<UserBo> getAllUsers() {

		List<User> list = this.repo.findAll();
		List<UserBo> listbo = new ArrayList<>();

		for (User user : list) {
			UserBo userbo = UsertoUserBo(user);
			listbo.add(userbo);
		}

		return listbo;
	}

	public UserBo getUserById(int userId) {
		Optional<User> optional = this.repo.findById(userId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("User", "userId", userId);
		}

		User user = optional.get();
		UserBo userbo = UsertoUserBo(user);
		return userbo;
	}

	public UserBo createUser(UserPayload user) {
		User u = new User();
		String name = user.getName();
		String password = user.getPassword();
		List<String> roles = user.getRoles();

		if (name.equals("") || password.equals("") || roles.size() == 0) {
			throw new InvalidInfoException("All Fields are Mandatory Please Enter Valid Data", HttpStatus.BAD_REQUEST);
		}

		u.setName(name);
		u.setPassword(password);

		List<Role> roleslist = this.rolerepo.findByNameIn(roles);

		if (roleslist.size() == 0) {
			throw new InvalidInfoException("Please Enter Valid Roles Data", HttpStatus.BAD_REQUEST);
		}

		u.setRoles(roleslist);
		this.repo.save(u);

		User users = this.repo.findByName(user.getName());
		UserBo userbo = UsertoUserBo(users);

		return userbo;
	}

	public UserBo updateUser(UserPayload user, int userId) {
		Optional<User> optional = this.repo.findById(userId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("User", "userId", userId);
		}

		User existingUser = optional.get();
		String name = user.getName();
		List<String> role = user.getRoles();

		if (name.equals("") || role.size() == 0) {
			throw new InvalidInfoException("All Fields are Mandatory Please Enter Valid Data", HttpStatus.BAD_REQUEST);
		}

		existingUser.setName(name);

		List<Role> roles = this.rolerepo.findByNameIn(role);

		if (roles.size() == 0) {
			throw new InvalidInfoException("Please Enter Valid Subjects Data", HttpStatus.BAD_REQUEST);
		}

		existingUser.setRoles(roles);

		this.repo.save(existingUser);
		UserBo userbo = UsertoUserBo(existingUser);
		return userbo;

	}

	public void deleteUser(int userId) {
		Optional<User> optional = this.repo.findById(userId);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("User", "userId", userId);
		}
		User existingUser = optional.get();
		this.repo.delete(existingUser);
		return;
	}

	public UserBo UsertoUserBo(User user) {
		UserBo userbo = this.modelMapper.map(user, UserBo.class);
		return userbo;
	}
}
