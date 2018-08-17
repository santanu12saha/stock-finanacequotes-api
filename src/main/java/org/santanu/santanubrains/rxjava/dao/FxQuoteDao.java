package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;

public class FxQuoteDao {

	public static FxQuote fetch(String fxSymbol) {

		try {
			return YahooFinance.getFx(fxSymbol);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
