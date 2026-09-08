package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.example.demo.EnrollmentServiceApplication;
import com.example.demo.client.CourseClient;
import com.example.demo.client.StudentClient;
import com.example.demo.dao.EnrollmentRepository;
import com.example.demo.dto.CourseDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.model.Enrollment;
import feign.FeignException;


@RequestMapping("enrollments")
@RestController
public class EnrollmentController {

 
	private final EnrollmentRepository enrollmentRepository;
    private final StudentClient studentClient;
    private final CourseClient courseClient;
    
    

	    public EnrollmentController(EnrollmentRepository enrollmentRepository, StudentClient studentClient,
			CourseClient courseClient) {
		this.enrollmentRepository = enrollmentRepository;
		this.studentClient = studentClient;
		this.courseClient = courseClient;
	}


		@PostMapping
        public ResponseEntity<?> createEnrollment(
        		               @RequestParam("status") String status ,
        		               @RequestParam("courseId")int courseId,
        		               @RequestParam("studentId")int studentId)throws Exception
        {
			
			StudentDto studentDto;
		    try {
		        studentDto = studentClient.findStudentById(studentId);
		    } catch (FeignException.NotFound e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student record not found");
		    }

		    CourseDto courseDto;
		    try {
		        courseDto = courseClient.findParticularCourse(courseId);
		    } catch (FeignException.NotFound e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course record not found");
		    }
			Enrollment enroll=new Enrollment();
			enroll.setCourseId(courseId);
			enroll.setStudentId(studentId);
			enroll.setEnrollmentDate(LocalDate.now());
			enroll.setStatus(status);
			
			enrollmentRepository.save(enroll);
			
			return ResponseEntity.ok(enroll);
			
		
}


	@GetMapping("/{id}")
	public Enrollment findParticularEnrollment(@PathVariable("id")int id)
	{
		 return enrollmentRepository.findById(id).orElse(null);
		 
	}
	
	@GetMapping
	public ResponseEntity<List<Enrollment>> getAllEnrollment()
	{
	List<Enrollment> en= enrollmentRepository.findAll();
	return ResponseEntity.ok(en);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEnrollment(@PathVariable("id")int id)
	{
		enrollmentRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Enrollment> updateEnrollment(@PathVariable("id")int id ,@RequestBody Enrollment enrollment)
	{
		Enrollment enroll=enrollmentRepository.findById(id).orElse(null);
		if(enroll==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		enroll.setCourseId(enrollment.getCourseId());
		enroll.setStudentId(enrollment.getStudentId());
		enroll.setStatus(enrollment.getStatus());
		
		 enrollmentRepository.save(enroll);
		 return ResponseEntity.ok(enroll);
		
	}
	
}
	
	
	


