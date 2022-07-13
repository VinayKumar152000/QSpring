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

import com.example.demo.domain.Subject;

import com.example.demo.services.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

	@Autowired
	SubjectService service;

	@GetMapping
	public List<Subject> getAllSubjects() {
		return service.getAllSubjects();
	}

	@GetMapping("/{id}")
	public Subject getSingleSubject(@PathVariable(value = "id") int subjectId){
		return service.getSubjectById(subjectId);
	}

	@PostMapping
	public Subject createSubject(@RequestBody Subject subject) {
		return service.createSubject(subject);
	}

	@PutMapping("/{id}")
	public Subject updateSubject(@RequestBody Subject subject, @PathVariable("id") int subjectId) {
		return service.updateSubject(subject, subjectId);
	}

	@DeleteMapping("/{id}")
	public void deleteSubject(@PathVariable("id") int subjectId) {
		service.deleteSubject(subjectId);
	}
}
