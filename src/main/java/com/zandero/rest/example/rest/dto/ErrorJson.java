package com.zandero.rest.example.rest.dto;

import com.zandero.utils.StringUtils;

/**
 *
 */
public class ErrorJson {

	public String message;

	public int code;

	public ErrorJson(int status, String text) {
		code = status;
		message = StringUtils.trimToNull(text);
	}
}
