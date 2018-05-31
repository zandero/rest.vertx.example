package com.zandero.rest.example.utils;

import com.zandero.rest.example.rest.dto.VersionDto;
import com.zandero.utils.InstantTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Properties;

/**
 *
 */
public final class VersionUtils {

	private static final Logger log = LoggerFactory.getLogger(VersionUtils.class);

	static final String VERSION_FILE = "version.txt";

	static public VersionDto version() {

		InputStream inputStream = VersionUtils.class.getClassLoader().getResourceAsStream(VERSION_FILE);

		try {

			if (inputStream != null) {
				Properties properties = new Properties();
				properties.load(inputStream);

				String version = properties.getProperty("version");
				String buildDate = properties.getProperty("build.date");

				Instant time = InstantTimeUtils.getTimestamp(buildDate, InstantTimeUtils.SHORT_TIME_FORMAT);

				return new VersionDto(version, time);

			} else {
				throw new FileNotFoundException("Property file '" + VERSION_FILE + "' not found in the classpath");
			}
		}
		catch (IOException e) {
			log.error("Failed to load version information.", e);
			throw new IllegalArgumentException("Missing version.txt property file!");
		}
	}
}
