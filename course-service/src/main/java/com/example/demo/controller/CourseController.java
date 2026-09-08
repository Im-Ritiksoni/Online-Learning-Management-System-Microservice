package com.example.demo.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CourseRepository;
import com.example.demo.model.Course;

@RequestMapping("/courses")
@RestController
public class CourseController {
	
	private final CourseRepository courseRepository;

	public CourseController(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	@PostMapping
	public ResponseEntity<Course> addCourse(@RequestBody Course course)
	{
		Course saveCourse=courseRepository.save(course);
		return new ResponseEntity<>(saveCourse, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Course> findParticularCourse(@PathVariable("id")int id)
	{
		Course course= courseRepository.findById(id).orElse(null);
		if(course==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		 return ResponseEntity.ok(course);
	}
	
	@GetMapping
	public ResponseEntity<List<Course>> findAllCourse()
	{
		List<Course> course=courseRepository.findAll();
	return ResponseEntity.ok(course);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteParticularId(@PathVariable("id")int id)
	{
		 courseRepository.deleteById(id);
		 return ResponseEntity.status(HttpStatus.OK).body("Record Deleted");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable("id")int id, @RequestBody Course course)
	{
		Course updatedCourse=courseRepository.findById(id).orElse(null);
		
		if(updatedCourse==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		updatedCourse.setCourseName(course.getCourseName());
		updatedCourse.setDuration(course.getDuration());
		updatedCourse.setFee(course.getFee());
		
		Course updated=courseRepository.save(updatedCourse);
		
		return ResponseEntity.ok(updated);
	}
	

}
