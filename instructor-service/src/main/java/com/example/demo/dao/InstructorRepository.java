package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {


}
