package com.shyftlabs.srm.service;

import java.util.List;

import com.shyftlabs.srm.exception.ServiceCheckedException;
import com.shyftlabs.srm.model.ResultDTO;
import com.shyftlabs.srm.request.AddResultRequest;

public interface IResultService {

	ResultDTO addResult(AddResultRequest request) throws ServiceCheckedException;

	List<ResultDTO> getAllResults();
}
