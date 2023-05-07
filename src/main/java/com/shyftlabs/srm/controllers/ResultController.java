package com.shyftlabs.srm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shyftlabs.srm.models.ResultDTO;
import com.shyftlabs.srm.request.AddResultRequest;
import com.shyftlabs.srm.services.IResultService;

import jakarta.validation.Valid;

@RestController
public class ResultController {

	@Autowired
	private IResultService resultService;
	
	@PostMapping("/results")
	public ResponseEntity<ResultDTO> addCourse(@Valid @RequestBody AddResultRequest request) {
		return ResponseEntity
		        .status(HttpStatus.CREATED)
		        .body(resultService.addResult(request));
	}

	@GetMapping("/results")
	public ResponseEntity<List<ResultDTO>> getAllResults() {
		return ResponseEntity
		        .status(HttpStatus.OK)
		        .body(resultService.getAllResults());
	}
}
