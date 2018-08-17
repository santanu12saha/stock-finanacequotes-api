package org.santanu.santanubrains.rxjava.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.inject.Inject;

import org.santanu.santanubrains.rxjava.dataAdapter.StockQuotePriceAdapter;

import io.reactivex.Single;

public class StockQuotePriceServiceImpl implements StockQuotePriceService{

	private StockQuotePriceAdapter stockQuotePriceAdapter;
	
	@Inject
	public StockQuotePriceServiceImpl(StockQuotePriceAdapter stockQuotePriceAdapter) {
		super();
		this.stockQuotePriceAdapter = stockQuotePriceAdapter;
	}

	@Override
	public Single<Map<String, BigDecimal>> getStockQuotePriceBySymbol(String symbol) {
		
		return stockQuotePriceAdapter.getStockQuotePriceByTicker(symbol);
	}

}
