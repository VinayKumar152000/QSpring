package com.example.demo.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
//	Teacher findByName(String name);

	List<Teacher> findByNameIn(Collection<String> sub);
}
