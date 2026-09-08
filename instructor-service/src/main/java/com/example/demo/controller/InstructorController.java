package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.InstructorRepository;
import com.example.demo.model.Instructor;


@RequestMapping("/instructors")
@RestController
public class InstructorController {
	
	private final InstructorRepository instructorRepository;
	
	
	
	public InstructorController(InstructorRepository instructorRepository) {
		this.instructorRepository = instructorRepository;
	}

	@PostMapping
	public ResponseEntity<Instructor> registerInstructor(@RequestBody Instructor instructor)
	{
		Instructor saveInstructor=instructorRepository.save(instructor);
		return new ResponseEntity<>(saveInstructor, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Instructor> findInstructorById(@PathVariable("id") int id)
	{
		Instructor ins= instructorRepository.findById(id).orElse(null);
		if(ins==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		
		 return ResponseEntity.ok(ins);
	}
	
	@GetMapping
	public ResponseEntity<List<Instructor>> findAllInstructors()
	{
          List<Instructor> getAllInstructors=instructorRepository.findAll();
		return ResponseEntity.ok(getAllInstructors);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteParticularId(@PathVariable("id")int id)
	{
		 instructorRepository.deleteById(id);
		 return ResponseEntity.status(HttpStatus.OK).body("Record Deleted");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Instructor> updateInstructor(@PathVariable("id")int id ,@RequestBody Instructor instructor)
	{
		Instructor ins= instructorRepository.findById(id).orElse(null);
		if(ins==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		ins.setName(instructor.getName());
		ins.setEmail(instructor.getEmail());
		ins.setSpecialization(instructor.getSpecialization());
		
		Instructor updatedInstructor=instructorRepository.save(ins);
	
		return ResponseEntity.ok(updatedInstructor);
	}
	
}

