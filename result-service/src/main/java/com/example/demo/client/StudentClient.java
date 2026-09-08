package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.studentDto;


@FeignClient(name="student-service")
public interface StudentClient {
	
	@GetMapping("/students/{id}")
	public  studentDto findStudentById(@PathVariable("id")int id);

}
