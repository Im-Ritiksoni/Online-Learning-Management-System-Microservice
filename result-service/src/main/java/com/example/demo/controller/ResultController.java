package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.AssignmentClient;
import com.example.demo.client.EnrollmentClient;
import com.example.demo.client.StudentClient;
import com.example.demo.dao.ResultRepository;
import com.example.demo.dto.AssignmentDto;
import com.example.demo.dto.EnrollmentDto;
import com.example.demo.dto.studentDto;
import com.example.demo.model.Result;


@RequestMapping("/results")
@RestController
public class ResultController {
	
	private final ResultRepository resultRepository;
	private final StudentClient studentClient;
	private final EnrollmentClient enrollmentClient;
	private final AssignmentClient assignmentClient;
	
	
	
	public ResultController(ResultRepository resultRepository, StudentClient studentClient,
			EnrollmentClient enrollmentClient, AssignmentClient assignmentClient) {
		this.resultRepository = resultRepository;
		this.studentClient = studentClient;
		this.enrollmentClient = enrollmentClient;
		this.assignmentClient = assignmentClient;
	}


     @PostMapping
	public ResponseEntity<Result> addResult(@RequestParam("AssignmentId")int assignmentId ,@RequestParam("EnrollmentId")int enrollmentId,
			@RequestParam("StudentId")int studentId, @RequestBody Result result)
	{
    	 AssignmentDto assignmentDto;
    	 try {
    	 assignmentDto=assignmentClient.findParticularAssignment(assignmentId);
    	 }
    	 catch(feign.FeignException.NotFound e) {
    		 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	 }
    	 EnrollmentDto enrollmentDto;
    	 try {
    		 enrollmentDto=enrollmentClient.findParticularEnrollment(enrollmentId);
    	 }
    	 catch(feign.FeignException.NotFound e) {
    		 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	 }
    	 studentDto studentDto;
    	 try {
    	 studentDto=studentClient.findStudentById(studentId);
    	 }
    	 catch(feign.FeignException.NotFound e) {
    		 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	 }
    	 
    	 
    	 Result res=new Result();
    	 res.setStudentId(studentId);
    	 res.setAssignmentId(assignmentId);
    	 res.setEnrollmentId(enrollmentId);
		 res.setResultDate(LocalDate.now());
		 res.setGrade(result.getGrade());
		 res.setMarks(result.getMarks());
			
			
		resultRepository.save(res);
			
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
     
     @GetMapping("/{id}")
    public ResponseEntity<Result> findParticularId(@PathVariable("id")int id)
    {
    	Result res=resultRepository.findById(id).orElse(null);
    	if(res==null)
    	{
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	return ResponseEntity.ok(res);
    }
     @GetMapping
  	public ResponseEntity<List<Result>> findAllEnrollment()
  	{
    	 List<Result> res= resultRepository.findAll();
 		return ResponseEntity.ok(res);
  		}
 	
 	@DeleteMapping("/{id}")
 	public ResponseEntity<Void> deleteEnrollment(@PathVariable("id")int id)
 	{
 		resultRepository.deleteById(id);
 		return ResponseEntity.status(HttpStatus.OK).build();
 	}
 	
 	@PutMapping("/{id}")
 	public ResponseEntity<Result> updateEnrollment(@PathVariable("id")int id ,@RequestBody Result result)
 	{
 		Result res=resultRepository.findById(id).orElse(null);
 		if(res==null)
 		{
 			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
 		}
 		
 		res.setAssignmentId(result.getAssignmentId());
 		res.setStudentId(result.getStudentId());
 		res.setEnrollmentId(result.getEnrollmentId());
 		res.setGrade(result.getGrade());
 		res.setMarks(result.getMarks());
 	
 		
 		
 		resultRepository.save(res);
 		 return ResponseEntity.ok(res);
 		
 	}

}
