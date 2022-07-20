package com.example.demo.services;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.demo.bo.SubjectBo;
import com.example.demo.bo.TeacherBo;
import com.example.demo.bo.UserBo;
import com.example.demo.domain.Subject;
import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
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

	@Autowired
	private ModelMapper modelMapper;

	public List<TeacherBo> getAllTeachers() {

		List<Teacher> list = this.repo.findAll();
		List<TeacherBo> listbo = new ArrayList<>();

		for (Teacher teacher : list) {
			TeacherBo teacherbo = TeachertoTeacherBo(teacher);
			listbo.add(teacherbo);
		}

		return listbo;
	}

	public TeacherBo getTeacherById(int teacherId) {
		Optional<Teacher> optional = this.repo.findById(teacherId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Teacher", "teacherId", teacherId);
		}

		Teacher teacher = optional.get();
		TeacherBo teacherbo = TeachertoTeacherBo(teacher);
		return teacherbo;
	}

	public TeacherBo createTeacher(TeacherPayload teacher) {
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

		// optimization
		List<Subject> subjects = this.subrepo.findByNameIn(sub);

		if (subjects.size() == 0) {
			throw new InvalidInfoException("Please Enter Valid Subjects Data", HttpStatus.BAD_REQUEST);
		}

		t.setSubjects(subjects);
		this.repo.save(t);

		Teacher teachers = this.repo.findByName(teacher.getName());
		TeacherBo teacherbo = TeachertoTeacherBo(teachers);

		return teacherbo;
	}

	public TeacherBo updateTeacher(TeacherPayload teacher, int teacherId) {
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

		this.repo.save(existingTeacher);
		TeacherBo teacherbo = TeachertoTeacherBo(existingTeacher);
		return teacherbo;

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

	public TeacherBo TeachertoTeacherBo(Teacher teacher) {
		TeacherBo teacherbo = this.modelMapper.map(teacher, TeacherBo.class);
		return teacherbo;
	}
}
