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
import com.example.demo.PaymentServiceApplication;
import com.example.demo.client.EnrollmentClient;
import com.example.demo.client.StudentClient;
import com.example.demo.dao.PaymentRepository;
import com.example.demo.dto.EnrollmentDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.model.Payment;

import feign.FeignException.FeignServerException;
import jakarta.ws.rs.POST;

@RequestMapping("/payments")
@RestController
public class PaymentController {

    private final PaymentServiceApplication paymentServiceApplication;
	
	private final PaymentRepository paymentRepository;
	private final StudentClient studentClient;
	private final EnrollmentClient enrollmentClient;
	
	
	public PaymentController(PaymentServiceApplication paymentServiceApplication, PaymentRepository paymentRepository,
			StudentClient studentClient, EnrollmentClient enrollmentClient) {
		this.paymentServiceApplication = paymentServiceApplication;
		this.paymentRepository = paymentRepository;
		this.studentClient = studentClient;
		this.enrollmentClient = enrollmentClient;
	}

	@PostMapping
	public ResponseEntity<Payment> createPayment(
			@RequestParam("status")String status ,
			@RequestParam("amount")int amount , 
			@RequestParam("enrollmentId")int enrollmentId,
			@RequestParam("studentId")int studentId)
	{
		StudentDto studentDto;
		try {
		studentDto=studentClient.findStudentById(studentId);}
		catch(feign.FeignException.NotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		EnrollmentDto enrollmentDto;
		try{
			enrollmentDto=enrollmentClient.findParticularEnrollment(enrollmentId);}
		catch(feign.FeignException.NotFound e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
		Payment pay=new Payment();
		pay.setStudentId(studentId);
		pay.setEnrollmentId(enrollmentId);
		pay.setPaymentDate(LocalDate.now());
		pay.setAmount(amount);
		pay.setStatus(status);
		
		paymentRepository.save(pay);
		
		return new ResponseEntity<>(pay, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Payment> findPaymentById(@PathVariable("id")int id)
	{
		Payment pay= paymentRepository.findById(id).orElse(null);
		if(pay==null)
		{
		 return ResponseEntity.status(HttpStatus.FOUND).build();
		}
		return ResponseEntity.ok(pay);
	}
	
	@GetMapping
	public ResponseEntity<List<Payment>> getAllPayment()
	{
		List<Payment> payment= paymentRepository.findAll();
		return ResponseEntity.ok(payment);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePayementById(@PathVariable("id")int id)
	{
		if(!paymentRepository.existsById(id)) {
		
		return ResponseEntity.status(HttpStatus.OK).body("record not found");
	}
         paymentRepository.deleteById(id);
         return ResponseEntity.status(HttpStatus.FOUND).body("Record deleted");
		}
	
	@PutMapping("/{id}")
	public ResponseEntity<Payment> updatePayment(@PathVariable("id")int id,@RequestBody Payment payment)
	{
		Payment pay=paymentRepository.findById(id).orElse(null);
		if(pay==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		pay.setStudentId(payment.getStudentId());
		pay.setEnrollmentId(payment.getEnrollmentId());
		pay.setAmount(payment.getAmount());
		pay.setStatus(payment.getStatus());
		
		paymentRepository.save(pay);
		return ResponseEntity.ok(pay);
		
	}
	
	

}
