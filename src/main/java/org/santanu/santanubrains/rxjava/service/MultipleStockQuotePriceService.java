package org.santanu.santanubrains.rxjava.service;

import java.math.BigDecimal;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;

public interface MultipleStockQuotePriceService {
	
	Single<Map<String, BigDecimal>> getMultipleStockQuotePriceBySymbols(MultipleStockQuery multipleStockQuery);

}
