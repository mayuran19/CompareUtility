package com.mayuran19.configuration.application;

import java.io.File;

import org.apache.log4j.Logger;

import com.mayuran19.configuration.common.exception.ApplicationException;
import com.mayuran19.configuration.xml.CompareHelper;

public class MainApplication {
	private static final Logger LOGGER = Logger
			.getLogger(MainApplication.class);

	public static void main(String[] args) {
		MainApplication mainApplication = new MainApplication();
		try {
			mainApplication.init();
		} catch (ApplicationException e) {
			LOGGER.error("Exception", e);
		}
	}

	private void init() throws ApplicationException {
		LOGGER.info("Starting the MainApplication");
		CompareHelper compareHelper = new CompareHelper();
		File file1 = new File(
				"/home/mayuran/Dropbox/workspace/java/compare/files/one/one.xml");
		File file2 = new File(
				"/home/mayuran/Dropbox/workspace/java/compare/files/two/two.xml");
		compareHelper.isEqual(file1, file2);
	}
}
