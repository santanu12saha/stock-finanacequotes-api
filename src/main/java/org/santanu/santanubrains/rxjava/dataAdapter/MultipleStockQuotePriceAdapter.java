package org.santanu.santanubrains.rxjava.dataAdapter;

import java.math.BigDecimal;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;

public interface MultipleStockQuotePriceAdapter {
	
	Single<Map<String, BigDecimal>> getMultipleStockQuotePriceBySymbols(MultipleStockQuery multipleStockQuery);
}
