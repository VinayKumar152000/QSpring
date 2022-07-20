package com.example.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.*;

import com.example.demo.bo.StudentBo;
import com.example.demo.bo.SubjectBo;
import com.example.demo.bo.UserBo;
import com.example.demo.domain.Student;
import com.example.demo.domain.Subject;
import com.example.demo.domain.Teacher;
import com.example.demo.domain.User;
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

	@Autowired
	private ModelMapper modelMapper;

	public List<StudentBo> getAllStudents() {

		List<Student> list = this.repo.findAll();
		List<StudentBo> listbo = new ArrayList<>();

		for (Student student : list) {
			StudentBo studentbo = StudenttoStudentBo(student);
			listbo.add(studentbo);
		}

		return listbo;
	}

	public StudentBo getStudentById(int studentId) {
		Optional<Student> optional = this.repo.findById(studentId);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Student", "studentId", studentId);
		}

		Student student = optional.get();
		StudentBo studentbo = StudenttoStudentBo(student);
		return studentbo;
	}

	public StudentBo createStudent(StudentPayload student) {
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
		this.repo.save(st);

		Student students = this.repo.findByName(student.getName());
		StudentBo studentbo = StudenttoStudentBo(students);

		return studentbo;
	}

	public StudentBo updateStudent(StudentPayload student, int studentId) {
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

		this.repo.save(existingStudent);
		StudentBo studentbo = StudenttoStudentBo(existingStudent);

		return studentbo;
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

	public StudentBo StudenttoStudentBo(Student student) {
		StudentBo subjectbo = this.modelMapper.map(student, StudentBo.class);
		return subjectbo;
	}
}
