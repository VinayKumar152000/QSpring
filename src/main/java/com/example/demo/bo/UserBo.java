package com.example.demo.bo;

import java.util.List;

import com.example.demo.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBo {

	private int userId;
	private String name;
	private List<RoleBo> roles;
}
