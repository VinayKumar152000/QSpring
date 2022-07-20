package com.example.demo.bo;

import java.util.List;

import com.example.demo.domain.Subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherBo {
	
	private int id;
	private String name;
	private List<SubjectBo> subjects;

}
