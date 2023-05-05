package com.shyftlabs.srm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyftlabs.srm.entities.Result;

public interface ResultRepository extends JpaRepository<Result,Long> {

}
