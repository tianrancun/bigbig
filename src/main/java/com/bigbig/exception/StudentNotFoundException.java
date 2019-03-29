package com.bigbig.exception;

public class StudentNotFoundException extends ServiceException {
	public StudentNotFoundException(String error) {
		super(error);
	}
}
