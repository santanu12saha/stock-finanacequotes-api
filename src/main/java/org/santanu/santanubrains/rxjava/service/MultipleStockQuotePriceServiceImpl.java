package org.santanu.santanubrains.rxjava.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockQuotePriceAdapter;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;

import io.reactivex.Single;

public class MultipleStockQuotePriceServiceImpl implements MultipleStockQuotePriceService{

	private MultipleStockQuotePriceAdapter multipleStockQuotePriceAdapter;
	
	@Inject
	public MultipleStockQuotePriceServiceImpl(MultipleStockQuotePriceAdapter multipleStockQuotePriceAdapter) {
		super();
		this.multipleStockQuotePriceAdapter = multipleStockQuotePriceAdapter;
	}

	@Override
	public Single<Map<String, BigDecimal>> getMultipleStockQuotePriceBySymbols(MultipleStockQuery multipleStockQuery) {
		
		return multipleStockQuotePriceAdapter.getMultipleStockQuotePriceBySymbols(multipleStockQuery);
	}

}
