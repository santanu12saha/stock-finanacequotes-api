package org.santanu.santanubrains.rxjava.domain;

import java.util.ArrayList;
import java.util.List;

public class FxMultipleQuoteQuery {

	private List<String> fxSymbols;

	public FxMultipleQuoteQuery() {
		fxSymbols = new ArrayList<>();
	}

	public FxMultipleQuoteQuery(List<String> fxSymbols) {
		super();
		this.fxSymbols = fxSymbols;
	}

	public List<String> getFxSymbols() {
		return fxSymbols;
	}

	public void setFxSymbols(List<String> fxSymbols) {
		this.fxSymbols = fxSymbols;
	}

	@Override
	public String toString() {
		return "FxMultipleQuoteQuery [fxSymbols=" + fxSymbols + "]";
	}

}
