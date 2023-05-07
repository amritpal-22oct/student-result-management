package com.shyftlabs.srm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyftlabs.srm.entities.Course;

public interface CourseRepository extends JpaRepository<Course,Long>{

	boolean existsByName(String name);
	
}
