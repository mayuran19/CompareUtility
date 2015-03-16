package com.mayuran19.configuration.common.utils;

import com.mayuran19.configuration.common.constant.Constants;
import com.mayuran19.configuration.common.exception.ApplicationException;

public class ConfigFileUtils extends PropertyFileUtils {

	public String readProperty(String key) throws ApplicationException {
		return super.readProperty(Constants.Paths.CONFIC_FILE_PATH, key);
	}

}
