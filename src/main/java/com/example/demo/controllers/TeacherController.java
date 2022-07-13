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

import com.example.demo.domain.Teacher;
import com.example.demo.payload.TeacherPayload;
import com.example.demo.services.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

	@Autowired
	TeacherService service;

	@GetMapping
	public List<Teacher> getAllTeachers() {
		return service.getAllTeachers();
	}

	@GetMapping("/{id}")
	public Teacher getSingleTeacher(@PathVariable(value = "id") int teacherId) {
		return service.getTeacherById(teacherId);
	}

	@PostMapping
	public Teacher createTeacher(@RequestBody TeacherPayload teacher) {
		return service.createTeacher(teacher);
	}

	@PutMapping("/{id}")
	public Teacher updateTeacher(@RequestBody TeacherPayload teacher, @PathVariable("id") int teacherId) {
		return service.updateTeacher(teacher, teacherId);
	}

	@DeleteMapping("/{id}")
	public void deleteTeacher(@PathVariable("id") int teacherId) {
		service.deleteTeacher(teacherId);
	}
}
