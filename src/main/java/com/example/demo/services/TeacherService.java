package com.example.demo.services;

import java.util.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Subject;
import com.example.demo.domain.Teacher;
import com.example.demo.exceptions.InvalidInfoException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payload.TeacherPayload;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.repository.TeacherRepository;

@Component
public class TeacherService {

	@Autowired
	private TeacherRepository repo;
	@Autowired
	private SubjectRepository subrepo;

	public List<Teacher> getAllTeachers() {
		return this.repo.findAll();
	}

	public Teacher getTeacherById(int teacherId) {
		Optional<Teacher> optional = this.repo.findById(teacherId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Teacher", "teacherId", teacherId);
		}

		Teacher teacher = optional.get();
		return teacher;
	}

	public Teacher createTeacher(TeacherPayload teacher) {
		Teacher t = new Teacher();
		String name = teacher.getName();
		List<String> sub = teacher.getSubjects();

		if (name.equals("") || sub.size() == 0) {
			throw new InvalidInfoException("All Fields are Mandatory Please Enter Valid Data", HttpStatus.BAD_REQUEST);
		}

		t.setName(name);
//		for (String subject : sub) {
//			if (this.subrepo.findByName(subject) != null) {
//				subjects.add(this.subrepo.findByName(subject));
//			}
//		}

		//optimization
		List<Subject> subjects= this.subrepo.findByNameIn(sub);
		
		if (subjects.size() == 0) {
			throw new InvalidInfoException("Please Enter Valid Subjects Data", HttpStatus.BAD_REQUEST);
		}

		t.setSubjects(subjects);
		return this.repo.save(t);
	}

	public Teacher updateTeacher(TeacherPayload teacher, int teacherId) {
		Optional<Teacher> optional = this.repo.findById(teacherId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Teacher", "teacherId", teacherId);
		}

		Teacher existingTeacher = optional.get();
		String name = teacher.getName();
		List<String> sub = teacher.getSubjects();

		if (name.equals("") || sub.size() == 0) {
			throw new InvalidInfoException("All Fields are Mandatory Please Enter Valid Data", HttpStatus.BAD_REQUEST);
		}

		existingTeacher.setName(name);
		
		List<Subject> subjects = this.subrepo.findByNameIn(sub);

		if (subjects.size() == 0) {
			throw new InvalidInfoException("Please Enter Valid Subjects Data", HttpStatus.BAD_REQUEST);
		}
		
		existingTeacher.setSubjects(subjects);

		return this.repo.save(existingTeacher);

	}

	public void deleteTeacher(int teacherId) {
		Optional<Teacher> optional = this.repo.findById(teacherId);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Teacher", "teacherId", teacherId);
		}
		Teacher existingTeacher = optional.get();
		this.repo.delete(existingTeacher);
		return;
	}
}
