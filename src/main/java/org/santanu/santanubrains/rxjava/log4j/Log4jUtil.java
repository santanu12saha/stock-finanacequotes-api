package org.santanu.santanubrains.rxjava.log4j;

import org.apache.log4j.Logger;

public class Log4jUtil {
	private static Logger logger;

	private Log4jUtil() {
		super();
	}

	public static synchronized Logger getLogger(Class<?> T) {

		if (logger == null) {
			logger = Logger.getLogger(T.getClass());
		}

		return logger;
	}
}
