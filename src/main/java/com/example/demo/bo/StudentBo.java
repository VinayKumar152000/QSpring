package com.example.demo.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentBo {

	private int id;
	private String name;

	private List<TeacherBo> teachers;

	private List<SubjectBo> subjects;
}
