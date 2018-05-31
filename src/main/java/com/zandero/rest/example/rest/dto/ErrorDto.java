package com.zandero.rest.example.rest.dto;

import com.zandero.utils.StringUtils;

/**
 * Simple error holding JSON class
 */
public class ErrorDto {

	private String message;

	private int code;

	public ErrorDto(int status, String text) {
		code = status;
		message = StringUtils.trimToNull(text);
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}
}
