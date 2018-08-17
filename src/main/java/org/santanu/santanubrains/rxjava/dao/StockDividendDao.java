package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.santanu.santanubrains.rxjava.utility.DateComparison;
import org.santanu.santanubrains.rxjava.utility.StringToCalender;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

public class StockDividendDao {

	public static StockDividend fetch(String ticker) {

		try {
			return YahooFinance.get(ticker).getDividend();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static List<HistoricalDividend> fetchDividendHistory(String ticker) {

		try {
			return YahooFinance.get(ticker).getDividendHistory();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static List<HistoricalDividend> fetchDividendHistoryFromDate(String ticker, String fromDate) {

		try {

			if (DateComparison.fromLesserCurrent(fromDate)) {
				return YahooFinance.get(ticker).getDividendHistory(StringToCalender.convert(fromDate));
			} else {
				throw new RuntimeException(
						"From Date: " + fromDate + " should be less than Current Date : " + new Date());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static List<HistoricalDividend> fetchDividendHistoryFromToDate(String ticker, String fromDate,
			String toDate) {

		try {

			if (DateComparison.fromDateLesserToDate(fromDate, toDate)) {

				if (DateComparison.fromLesserCurrent(fromDate)) {
					if(DateComparison.toDateLesserCurrent(toDate)) {
						return YahooFinance.get(ticker).getDividendHistory(StringToCalender.convert(fromDate),
								StringToCalender.convert(toDate));
					}else {
						throw new RuntimeException(
								"To Date: " + toDate + " should be less than or equal to Current Date : " + new Date());
					}
					
				} else {
					throw new RuntimeException(
							"From Date: " + fromDate + " should be less than or equal to Current Date : " + new Date());
				}

			} else {
				throw new RuntimeException("From Date : " + fromDate + " should be less than To Date : " + toDate);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
