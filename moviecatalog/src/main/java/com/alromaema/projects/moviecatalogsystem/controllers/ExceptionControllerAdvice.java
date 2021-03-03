package com.alromaema.projects.moviecatalogsystem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exception(Exception e) {
		ObjectMapper mapper = new ObjectMapper();
		Error error = new Error(e);
		String js_resp = "{}";
		try {
			js_resp = mapper.writeValueAsString(error);
		} catch (JsonProcessingException ee) {
			ee.printStackTrace();
		}
		return ResponseEntity.badRequest().body(js_resp);
	}
	
	private class Error {
	    public final String cn;
	    public final String exm;

	    public Error(Exception x) {
	        this.cn = x.getClass().getName();
	        this.exm = x.getLocalizedMessage();
	    }
	}
}
