package org.santanu.santanubrains.rxjava.main;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.santanu.santanubrains.rxjava.utility.StringToCalender;

import com.google.gson.Gson;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.stock.StockStats;

public class MultipleStockFetcher {
	
	public static Map<String, Stock> fetch(List<String> symbols){
		
		try {
			Map<String, Stock> stocks = YahooFinance.get(symbols.toArray(new String[symbols.size()]));
			
			/*StockQuote stockquote = stocks.get(symbols.get(0)).getQuote();
			Gson gson = new Gson();
			System.out.println(gson.toJson(stockquote));*/
			
			Gson gson = new Gson();
			String[] fxSymbols = new String[] {"NZDAUD=X","USDJPY=X","USDNZD=X","HKDUSD=X","CHFHKD=X"};
			Map<String, FxQuote> fxQuote = YahooFinance.getFx(fxSymbols);
			System.out.println(gson.toJson(fxQuote));
			List<HistoricalDividend> hisdata = stocks.get(symbols.get(6)).getDividendHistory(StringToCalender.convert("2017-02-02"));
			System.out.println(gson.toJson(hisdata));
			
			StockStats stockStats = YahooFinance.get(symbols.get(6)).getStats();
			System.out.println(gson.toJson(stockStats));
			
			System.out.println(gson.toJson(YahooFinance.get("GOOG").getSplitHistory()));
			
			return stocks;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
