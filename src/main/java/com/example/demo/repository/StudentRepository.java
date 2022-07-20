package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Student;
import com.example.demo.domain.Teacher;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	Student findByName(String name);
}
