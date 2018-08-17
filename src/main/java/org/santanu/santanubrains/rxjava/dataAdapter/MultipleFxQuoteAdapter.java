package org.santanu.santanubrains.rxjava.dataAdapter;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.FxMultipleQuoteQuery;

import io.reactivex.Single;
import yahoofinance.quotes.fx.FxQuote;

public interface MultipleFxQuoteAdapter {
	
	Single<Map<String,FxQuote>> getMultipleFxQuotesBySymbols(FxMultipleQuoteQuery fxMultipleQuoteQuery);
}
