package com.shyftlabs.srm.util;

public class ConstantsUtil {

	public static class ErrorCodes {
		public static final String STUDENT_DUPLICATE_EMAIL = "SRM_300";
		public static final String COURSE_DUPLICATE_NAME = "SRM_301";
		public static final String RESULT_DUPLICATE_STUDENT_AND_COURSE = "SRM_302";
		public static final String STUDENT_NOT_EXISTS_WITH_ID = "SRM_401";
		public static final String COURSE_NOT_EXISTS_WITH_ID = "SRM_402";
		public static final String INTERNAL_SERVER_ERROR = "SRM_500";
		public static final String OPTIMISTIC_FAILURE = "SRM_501";
		public static final String REQUEST_VALIDATION_FAILURE = "SRM_400";
	}
}
