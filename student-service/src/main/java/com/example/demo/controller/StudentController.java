package com.example.demo.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.StudentRepository;

import com.example.demo.model.Student;



@RequestMapping("/students")
@RestController
public class StudentController {
	
	private final StudentRepository studentRepository;
	
	
	
	public StudentController(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@PostMapping
	public ResponseEntity<Student> addStudent(@RequestBody Student student)
	{
		Student saveStudent= studentRepository.save(student);
		return new ResponseEntity<>(saveStudent, org.springframework.http.HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public  ResponseEntity<Student> findStudentById(@PathVariable("id")int id)
	{
		
		Student student= studentRepository.findById(id).orElse(null);
		if(student==null)
		{
		return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build();	
		}
		 return ResponseEntity.ok(student);	
	}
	
	@GetMapping
	public ResponseEntity<List<Student>> findAllStudent()
	{
		List<Student> student= studentRepository.findAll();
		return ResponseEntity.ok(student);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteParticularId(@PathVariable("id")int id)
	{
		 studentRepository.deleteById(id);
		 return ResponseEntity.status(org.springframework.http.HttpStatus.OK).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable("id")int id,@RequestBody Student stu)
	{
		Student student=studentRepository.findById(id).orElse(null);
		if(student==null)
		{
 return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build();
		}
			student.setEmail(stu.getEmail());
			student.setMobile(stu.getMobile());
			student.setName(stu.getName());
			
			Student updatedStudent=  studentRepository.save(student);	
		
		return ResponseEntity.ok(updatedStudent);
	}
	
	
	

}
