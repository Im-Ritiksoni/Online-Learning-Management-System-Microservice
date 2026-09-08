package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.EnrollmentDto;


@FeignClient(name="enrollment-service")
public interface EnrollmentClient {
	
	@GetMapping("/enrollments/{id}")
	public EnrollmentDto findParticularEnrollment(@PathVariable("id")int id);

}
