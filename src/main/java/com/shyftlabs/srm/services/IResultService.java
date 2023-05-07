package com.shyftlabs.srm.services;

import java.util.List;

import com.shyftlabs.srm.exceptions.ServiceBaseException;
import com.shyftlabs.srm.models.ResultDTO;
import com.shyftlabs.srm.request.AddResultRequest;

public interface IResultService {

	ResultDTO addResult(AddResultRequest request) throws ServiceBaseException;

	List<ResultDTO> getAllResults();
}
