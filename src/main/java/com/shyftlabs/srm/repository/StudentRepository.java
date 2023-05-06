package com.shyftlabs.srm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyftlabs.srm.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	boolean existsByEmail(String email);

}
