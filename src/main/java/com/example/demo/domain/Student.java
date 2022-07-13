package com.example.demo.domain;

import java.util.*;
import javax.persistence.*;
import lombok.*;;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "student_info")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "student_id")
	private int id;
	private String name;

	@ManyToMany
	@JoinTable(name = "student_teacher_info", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	private List<Teacher> teachers;

	@ManyToMany
	@JoinTable(name = "student_subject_info", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<Subject> subjects;

}
