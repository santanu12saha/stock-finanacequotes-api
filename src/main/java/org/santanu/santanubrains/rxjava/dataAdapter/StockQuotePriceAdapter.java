package org.santanu.santanubrains.rxjava.dataAdapter;

import java.math.BigDecimal;
import java.util.Map;

import io.reactivex.Single;

public interface StockQuotePriceAdapter {
	
	Single<Map<String, BigDecimal>> getStockQuotePriceByTicker(String ticker);
}
