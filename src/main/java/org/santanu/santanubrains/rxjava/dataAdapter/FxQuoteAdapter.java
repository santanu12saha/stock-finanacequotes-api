package org.santanu.santanubrains.rxjava.dataAdapter;

import io.reactivex.Single;
import yahoofinance.quotes.fx.FxQuote;

public interface FxQuoteAdapter {
	
	Single<FxQuote> getFxQuoteBySysmbol(String fxSymbol);
}
