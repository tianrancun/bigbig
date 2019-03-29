package com.bigbig.util.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {
	
	private Status status = Status.SUCCESS;

	private String errorMessage;
	
	private T payload;
	
	private String printsPayload;
	
	public ServiceResponse(T payload){
		this.payload = payload;
	}
	
	public ServiceResponse(String errorMessage) {
		this.status = Status.ERROR;
		this.errorMessage = errorMessage;
	}
}
