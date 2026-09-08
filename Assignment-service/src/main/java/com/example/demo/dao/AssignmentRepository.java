package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

}
