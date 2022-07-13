package com.example.demo.payload;

import java.util.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentPayload {

	private String name;
	private List<String> teachers;
	private List<String> subjects;

}
