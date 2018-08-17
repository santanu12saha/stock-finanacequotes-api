package org.santanu.santanubrains.rxjava.service;

import java.math.BigDecimal;
import java.util.Map;

import io.reactivex.Single;

public interface StockQuotePriceService {
	
	Single<Map<String, BigDecimal>> getStockQuotePriceBySymbol(String symbol);
}
