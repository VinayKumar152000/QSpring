package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.demo.bo.RoleBo;
import com.example.demo.bo.SubjectBo;
import com.example.demo.domain.Role;
import com.example.demo.domain.Subject;
import com.example.demo.exceptions.InvalidInfoException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.SubjectRepository;

@Component
public class SubjectService {

	@Autowired
	private SubjectRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public List<SubjectBo> getAllSubjects() {

		List<Subject> list = this.repo.findAll();
		List<SubjectBo> listbo = new ArrayList<>();

		for (Subject subject : list) {
			SubjectBo subjectbo = SubjecttoSubjectBo(subject);
			listbo.add(subjectbo);
		}

		return listbo;
	}

	public SubjectBo getSubjectById(int subjectId) {
		Optional<Subject> optional = this.repo.findById(subjectId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Subject", "subjectId", subjectId);
		}

		Subject subject = optional.get();
		SubjectBo subjectbo = SubjecttoSubjectBo(subject);

		return subjectbo;

	}

	public SubjectBo createSubject(Subject subject) {

		if (subject.getName().equals("")) {
			throw new InvalidInfoException("Please Enter a Valid Subject Info", HttpStatus.BAD_REQUEST);
		}
		this.repo.save(subject);
		SubjectBo subjectbo = SubjecttoSubjectBo(subject);

		return subjectbo;
	}

	public SubjectBo updateSubject(Subject subject, int subjectId) {
		Optional<Subject> optional = this.repo.findById(subjectId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Subject", "subjectId", subjectId);
		}
		if (subject.getName().equals("")) {
			throw new InvalidInfoException("Please Enter a Valid Subject Info", HttpStatus.BAD_REQUEST);
		}
		Subject existingSubject = optional.get();
		existingSubject.setName(subject.getName());

		this.repo.save(existingSubject);

		SubjectBo subjectbo = SubjecttoSubjectBo(existingSubject);
		return subjectbo;

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

	public SubjectBo SubjecttoSubjectBo(Subject subject) {
		SubjectBo subjectbo = this.modelMapper.map(subject, SubjectBo.class);
		return subjectbo;
	}
}
