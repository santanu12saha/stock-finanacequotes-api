package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;
import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.FxMultipleQuoteQuery;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;

public class MultipleFxQuotesDao {

	public static Map<String,FxQuote> fetch(FxMultipleQuoteQuery fxMultipleQuoteQuery) {
		System.out.println(fxMultipleQuoteQuery);
		try {
			return YahooFinance.getFx(fxMultipleQuoteQuery.getFxSymbols()
					.toArray(new String[fxMultipleQuoteQuery.getFxSymbols().size()]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
