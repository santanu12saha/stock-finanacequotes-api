package org.santanu.santanubrains.rxjava.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public abstract class StockCache {

	private LoadingCache<String, Object> stockCache = null;

	protected StockCache(long maxSize, long expireInMinutes) {
		init(maxSize, expireInMinutes);
	}

	private void init(long maxSize, long expireInMinutes) {
		RemovalListener<String, Object> removalListener = new RemovalListener<String, Object>() {

			@Override
			public void onRemoval(RemovalNotification<String, Object> removal) {
				if (removal.getCause() == RemovalCause.EXPIRED) {
					processAfterExpire(removal.getKey(), removal.getValue());
				} else if (removal.getCause() == RemovalCause.REPLACED) {
					processAfterReplace(removal.getKey(), removal.getValue());
				} else {
					// TODO if necessary
				}

			}
		};

		stockCache = CacheBuilder.newBuilder().maximumSize(maxSize).expireAfterWrite(expireInMinutes, TimeUnit.MINUTES)
				.removalListener(removalListener).build(new CacheLoader<String, Object>() {

					@Override
					public Object load(String key) throws Exception {
						// TODO Auto-generated method stub
						return getUnchecked(key);
					}

				});

	}

	public Object getUnchecked(String key) {
		return stockCache.getUnchecked(key);
	}

	public void add(String key, Object o) {
		stockCache.put(key, o);
	}

	public void delete(String key) {
		stockCache.invalidate(key);
	}

	public long getSize() {
		return stockCache.size();
	}

	public void cleanup() {
		stockCache.cleanUp();
	}

	public ConcurrentMap<String, Object> getAsMap() {
		return stockCache.asMap();
	}

	public LoadingCache<String, Object> getCache() {
		return stockCache;
	}

	public abstract void processAfterExpire(String key, Object obj);

	public abstract void processAfterReplace(String key, Object obj);

}
