package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Subject;
import com.example.demo.exceptions.InvalidInfoException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.SubjectRepository;

@Component
public class SubjectService {

	@Autowired
	private SubjectRepository repo;

	public List<Subject> getAllSubjects() {

		return this.repo.findAll();
	}

	public Subject getSubjectById(int subjectId) {
		Optional<Subject> optional = this.repo.findById(subjectId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Subject", "subjectId", subjectId);
		}

		Subject subject = optional.get();

		return subject;

	}

	public Subject createSubject(Subject subject) {

		if (subject.getName().equals("")) {
			throw new InvalidInfoException("Please Enter a Valid Subject Info", HttpStatus.BAD_REQUEST);
		}
		return this.repo.save(subject);
	}

	public Subject updateSubject(Subject subject, int subjectId) {
		Optional<Subject> optional = this.repo.findById(subjectId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Subject", "subjectId", subjectId);
		}
		if (subject.getName().equals("")) {
			throw new InvalidInfoException("Please Enter a Valid Subject Info", HttpStatus.BAD_REQUEST);
		}
		Subject existingSubject = optional.get();
		existingSubject.setName(subject.getName());

		return this.repo.save(existingSubject);

	}

	public String deleteSubject(int subjectId) {
		Optional<Subject> optional = this.repo.findById(subjectId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Subject", "subjectId", subjectId);
		}

		Subject existingSubject = optional.get();
		this.repo.delete(existingSubject);
		return "Subject is Deleted with " + subjectId;
	}
}
