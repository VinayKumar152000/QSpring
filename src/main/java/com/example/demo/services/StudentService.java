package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.*;

import com.example.demo.domain.Student;
import com.example.demo.domain.Subject;
import com.example.demo.domain.Teacher;
import com.example.demo.exceptions.InvalidInfoException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payload.StudentPayload;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.repository.TeacherRepository;

@Component
public class StudentService {

	@Autowired
	private StudentRepository repo;
	@Autowired
	private TeacherRepository teachrepo;
	@Autowired
	private SubjectRepository subrepo;

	public List<Student> getAllStudents() {
		return this.repo.findAll();
	}

	public Student getStudentById(int studentId) {
		Optional<Student> optional = this.repo.findById(studentId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Student", "studentId", studentId);
		}

		Student student = optional.get();
		return student;
	}

	public Student createStudent(StudentPayload student) {
		Student st = new Student();

		String name = student.getName();
		List<String> sub = student.getSubjects();
		List<String> teach = student.getTeachers();

		if (name.equals("") || sub.size() == 0 || teach.size() == 0) {
			throw new InvalidInfoException("All Fields are Mandatory Please Enter Valid Data", HttpStatus.BAD_REQUEST);
		}

		st.setName(student.getName());

		List<Subject> subjects = this.subrepo.findByNameIn(sub);
		List<Teacher> teachers = this.teachrepo.findByNameIn(teach);

		if (subjects.size() == 0 || teachers.size() == 0) {
			throw new InvalidInfoException("Please Enter Valid Subjects or Teachers Data", HttpStatus.BAD_REQUEST);
		}
		st.setSubjects(subjects);
		st.setTeachers(teachers);
		return this.repo.save(st);
	}

	public Student updateStudent(StudentPayload student, int studentId) {
		Optional<Student> optional = this.repo.findById(studentId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Student", "studentId", studentId);
		}
		Student existingStudent = optional.get();

		String name = student.getName();
		List<String> sub = student.getSubjects();
		List<String> teach = student.getTeachers();

		if (name.equals("") || sub.size() == 0 || teach.size() == 0) {
			throw new InvalidInfoException("All Fields are Mandatory Please Enter Valid Data", HttpStatus.BAD_REQUEST);
		}

		existingStudent.setName(name);

		List<Subject> subjects = this.subrepo.findByNameIn(sub);
		List<Teacher> teachers = this.teachrepo.findByNameIn(teach);

		if (subjects.size() == 0 || teachers.size() == 0) {
			throw new InvalidInfoException("Please Enter Valid Subjects Data", HttpStatus.BAD_REQUEST);
		}

		existingStudent.setSubjects(subjects);
		existingStudent.setTeachers(teachers);

		return this.repo.save(existingStudent);

	}

	public void deleteStudent(int studentId) {
		Optional<Student> optional = this.repo.findById(studentId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Student", "studentId", studentId);
		}
		Student existingStudent = optional.get();
		this.repo.delete(existingStudent);
		return;
	}
}
