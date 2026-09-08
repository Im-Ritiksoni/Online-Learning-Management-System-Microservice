package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.InstructorDto;


@FeignClient(name="instructor-service")
public interface InstructorClient {
    @GetMapping("/instructors/{id}")   
    public InstructorDto findInstructorById(@PathVariable("id") int id);

}
