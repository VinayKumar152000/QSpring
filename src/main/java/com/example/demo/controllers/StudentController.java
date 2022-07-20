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

import com.example.demo.bo.StudentBo;
import com.example.demo.domain.Student;
import com.example.demo.payload.StudentPayload;
import com.example.demo.services.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	@Autowired
	StudentService service;

	@GetMapping
	public List<StudentBo> getAllStudents() {
		return service.getAllStudents();
	}

	@GetMapping("/{id}")
	public StudentBo getSingleStudent(@PathVariable(value = "id") int studentId) {
		return service.getStudentById(studentId);
	}

	@PostMapping
	public StudentBo createStudent(@RequestBody StudentPayload student) {
		return service.createStudent(student);
	}

	@PutMapping("/{id}")
	public StudentBo updateStudent(@RequestBody StudentPayload student, @PathVariable("id") int studentId) {
		return service.updateStudent(student, studentId);
	}

	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable("id") int studentId) {
		service.deleteStudent(studentId);
	}
}
