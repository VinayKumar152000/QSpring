package com.example.demo.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

//	Subject findByName(String name);
	List<Subject> findByNameIn(Collection<String> sub);
}
