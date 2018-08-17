package org.santanu.santanubrains.rxjava.domain;

import java.util.ArrayList;
import java.util.List;

public class MultipleStockQuery {

	private List<String> stockSymbols;

	public MultipleStockQuery() {
		stockSymbols = new ArrayList<>();
	}

	public MultipleStockQuery(List<String> stockSymbols) {
		super();
		this.stockSymbols = stockSymbols;
	}

	public List<String> getStockSymbols() {
		return stockSymbols;
	}

	public void setStockSymbols(List<String> stockSymbols) {
		this.stockSymbols = stockSymbols;
	}

}
