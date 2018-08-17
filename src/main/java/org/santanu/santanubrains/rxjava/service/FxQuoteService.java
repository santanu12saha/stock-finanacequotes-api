package org.santanu.santanubrains.rxjava.service;

import io.reactivex.Single;
import yahoofinance.quotes.fx.FxQuote;

public interface FxQuoteService {
	
	Single<FxQuote> getFxQuoteBySymbol(String fxSymbol);

}
