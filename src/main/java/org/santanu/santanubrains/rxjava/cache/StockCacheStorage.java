package org.santanu.santanubrains.rxjava.cache;

import java.util.Date;

import javax.inject.Singleton;
import org.apache.log4j.Logger;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;

@Singleton
public class StockCacheStorage extends StockCache{

	private static final Logger logger = Log4jUtil.getLogger(StockCacheStorage.class);
	private final static long EXPIRE_IN_MINUTES = 10; // Timeout after 10 minutes
	private final static long MAX_SIZE = 1000;

	public StockCacheStorage() {
		super(MAX_SIZE, EXPIRE_IN_MINUTES);
	}

	@Override
	public void processAfterExpire(String key, Object obj) {
		logger.info("TIMEOUT OCCURED! Key: " + key + ", Date: " + new Date().toString());

	}

	@Override
	public void processAfterReplace(String key, Object obj) {
		logger.info("REPLACE OCCURED! Key: " + key + ", Date: " + new Date().toString());

	}

}
