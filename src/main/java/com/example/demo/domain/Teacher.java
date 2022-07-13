package com.example.demo.domain;

import java.util.*;
import javax.persistence.*;
import lombok.*;

import javax.persistence.JoinColumn;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "teacher_info")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "teacher_id")
	private int id;
	private String name;

	@ManyToMany
	@JoinTable(name = "teacher_subject_info", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	private List<Subject> subjects;

}
