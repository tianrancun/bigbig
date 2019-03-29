package com.bigbig.util.common;

public enum Status {
	
	SUCCESS(200),ERROR(500),NOT_FOUND(404);

	private int response;
	
	private Status(int response) {
		this.response = response;
	}
}
