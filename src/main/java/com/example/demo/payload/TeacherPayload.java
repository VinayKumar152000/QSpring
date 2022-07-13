package com.example.demo.payload;

import java.util.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherPayload {

	private String name;
	private List<String> subjects;
}
