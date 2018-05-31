package com.zandero.rest.example.rest.dto;

import com.zandero.utils.InstantTimeUtils;

import java.time.Instant;

/**
 *
 */
public class VersionDto {

	private String version;

	private String date;

	private VersionDto() {
		// for deserialization purposes only
	}

	public VersionDto(String versionNumber, Instant time) {
		version = versionNumber;
		date = InstantTimeUtils.formatDateTimeShort(time);
	}

	public String getVersion() {
		return version;
	}

	public String getDate() {
		return date;
	}
}
