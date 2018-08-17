package org.santanu.santanubrains.rxjava.domain;

import java.util.ArrayList;
import java.util.List;

public class MultipleStockQuotesQuery {

	private List<String> stockQuoteSymbols;

	public MultipleStockQuotesQuery() {
		stockQuoteSymbols = new ArrayList<>();
	}

	public MultipleStockQuotesQuery(List<String> stockQuoteSymbols) {
		super();
		this.stockQuoteSymbols = stockQuoteSymbols;
	}

	public List<String> getStockQuoteSymbols() {
		return stockQuoteSymbols;
	}

	public void setStockQuoteSymbols(List<String> stockQuoteSymbols) {
		this.stockQuoteSymbols = stockQuoteSymbols;
	}

	@Override
	public String toString() {
		return "MultipleStockQuotesQuery [stockQuoteSymbols=" + stockQuoteSymbols + "]";
	}

}
