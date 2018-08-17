package org.santanu.santanubrains.rxjava.service;

import java.util.Map;

import org.santanu.santanubrains.rxjava.domain.FxMultipleQuoteQuery;

import io.reactivex.Single;
import yahoofinance.quotes.fx.FxQuote;

public interface MultipleFxQuoteService {
	
	Single<Map<String, FxQuote>> getMultipleFxQuotesBySymbol(FxMultipleQuoteQuery fxMultipleQuoteQuery);
}
