package com.mayuran19.configuration.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mayuran19.configuration.common.exception.ApplicationException;

public class PropertyFileUtils {
	private static final Logger LOGGER = Logger
			.getLogger(PropertyFileUtils.class);

	public String readProperty(String filePath, String key)
			throws ApplicationException {
		Properties properties = new Properties();
		InputStream inputStream = PropertyFileUtils.class
				.getResourceAsStream(filePath);
		try {
			properties.load(inputStream);
			String value = properties.getProperty(key);
			inputStream.close();
			return value;
		} catch (IOException e) {
			LOGGER.error("Exception", e);
			throw new ApplicationException(e);
		}
	}
}
