package com.shyftlabs.srm.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MapperUtils {

	static ModelMapper modelMapper = new ModelMapper();
	static {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	    return source
	      .stream()
	      .map(element -> modelMapper.map(element, targetClass))
	      .collect(Collectors.toList());
	}
	
	public static <S, T> T mapObject(S source, Class<T> targetClass) {
	    return modelMapper.map(source, targetClass);
	}
}
