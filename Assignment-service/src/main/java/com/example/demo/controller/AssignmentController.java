package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.AssignmentServiceApplication;
import com.example.demo.client.CourseClient;
import com.example.demo.client.InstructorClient;
import com.example.demo.dao.AssignmentRepository;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.InstructorDto;
import com.example.demo.model.Assignment;

import feign.Feign;
import feign.FeignException;



@RequestMapping("/assignments")
@RestController
public class AssignmentController {

    private final AssignmentServiceApplication assignmentServiceApplication;
	
	private final AssignmentRepository assignmentRepository;
	private final CourseClient courseClient;
	private final InstructorClient instructorClient;
	

	public AssignmentController(AssignmentRepository assignmentRepository, CourseClient courseClient,
			InstructorClient instructorClient, AssignmentServiceApplication assignmentServiceApplication) {
		this.assignmentRepository = assignmentRepository;
		this.courseClient = courseClient;
		this.instructorClient = instructorClient;
		this.assignmentServiceApplication = assignmentServiceApplication;
	}

	@PostMapping
	public ResponseEntity<Assignment> addAssignment(
			@RequestParam("courseId")int courseId,
			@RequestParam("instructorId")int instructorId, 
			@RequestBody Assignment assignment)
	{
		 
		
		
		CourseDto courseDto;
	    try {
	        courseDto = courseClient.findParticularCourse(courseId);
	    } catch (feign.FeignException.NotFound e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }

	    InstructorDto instructorDto;
	    try {
	        instructorDto = instructorClient.findInstructorById(instructorId);
	    } catch (feign.FeignException.NotFound e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
		
		
		Assignment assi=new Assignment();
		
		assi.setCourseId(courseId);
		assi.setInstructorId(instructorId);
		assi.setDueDate(LocalDate.now());
		assi.setTitle(assignment.getTitle());
		assi.setDescription(assignment.getDescription());
		
		 assignmentRepository.save(assi);
		 
		 return ResponseEntity.ok(assi);
	}
	
	@GetMapping
	public ResponseEntity<List<Assignment>> findAllAssignment()
	{
		List<Assignment> student= assignmentRepository.findAll();
		return ResponseEntity.ok(student);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Assignment> findParticularAssignment(@PathVariable("id")int id)
	{
		Assignment assignment= assignmentRepository.findById(id).orElse(null);
		if(assignment==null)
		{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
		return ResponseEntity.ok(assignment);
		}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteParticularId(@PathVariable("id")int id)
	{
		assignmentRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Assignment> updateAssignment(@PathVariable("id")int id,@RequestBody Assignment assignment)
	{
		Assignment assign=assignmentRepository.findById(id).orElse(null);
		if(assign==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		assign.setCourseId(assignment.getCourseId());
		assign.setInstructorId(assignment.getInstructorId());
		assign.setTitle(assignment.getTitle());
		assign.setDescription(assignment.getDescription());
				
		assignmentRepository.save(assign);
		return ResponseEntity.ok(assign);
		
	}
	
	@ExceptionHandler
	public String myMsg()
	{
		return "Record not founnd";
	}
	

}
