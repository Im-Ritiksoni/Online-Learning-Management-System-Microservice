package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {

}
