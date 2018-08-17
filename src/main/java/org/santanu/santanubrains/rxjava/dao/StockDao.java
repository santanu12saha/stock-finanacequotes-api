package org.santanu.santanubrains.rxjava.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.santanu.santanubrains.rxjava.utility.DateComparison;
import org.santanu.santanubrains.rxjava.utility.SpecifiedInterval;
import org.santanu.santanubrains.rxjava.utility.StringToCalender;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class StockDao {

	public static Stock fetch(String ticker) {

		try {
			return YahooFinance.get(ticker);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Stock fetchStockWithHistoricalData(String ticker, boolean includeHistorical) {

		try {
			return YahooFinance.get(ticker, includeHistorical);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Stock fetchStockByFromDate(String ticker, String fromDate) {
		try {
			if (DateComparison.fromLesserCurrent(fromDate)) {
				return YahooFinance.get(ticker, StringToCalender.convert(fromDate));
			} else {
				throw new RuntimeException(
						"From Date: " + fromDate + " should be less than Current Date : " + new Date());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Stock fetchStockByInterval(String ticker, String interval) {
		try {
			if (SpecifiedInterval.interval(interval) != null) {
				return YahooFinance.get(ticker, SpecifiedInterval.interval(interval));
			} else {
				throw new RuntimeException("InValid Interval. Options are 'Weekly','Daily','Monthly'");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Stock fetchStockByFromToDate(String ticker, String fromDate, String toDate) {

		try {
			if (DateComparison.fromDateLesserToDate(fromDate, toDate)) {

				if (DateComparison.fromLesserCurrent(fromDate)) {

					if (DateComparison.toDateLesserCurrent(toDate)) {

						return YahooFinance.get(ticker, StringToCalender.convert(fromDate),
								StringToCalender.convert(toDate));

					} else {
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

	public static Stock fetchStockByFromInterval(String ticker, String fromDate, String interval) {

		try {
			if (DateComparison.fromLesserCurrent(fromDate)) {

				if (SpecifiedInterval.interval(interval) != null) {

					return YahooFinance.get(ticker, StringToCalender.convert(fromDate),
							SpecifiedInterval.interval(interval));
				} else {
					throw new RuntimeException("InValid Interval. Options are 'Weekly','Daily','Monthly'");
				}
			} else {
				throw new RuntimeException(
						"From Date: " + fromDate + " should be less than Current Date : " + new Date());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Stock fetchStockByFromToDateInterval(String ticker, String fromDate, String toDate, String interval) {

		try {
			if (DateComparison.fromDateLesserToDate(fromDate, toDate)) {

				if (DateComparison.fromLesserCurrent(fromDate)) {

					if (DateComparison.toDateLesserCurrent(toDate)) {

						if (SpecifiedInterval.interval(interval) != null) {
							return YahooFinance.get(ticker, StringToCalender.convert(fromDate),
									StringToCalender.convert(toDate), SpecifiedInterval.interval(interval));
						} else {
							throw new RuntimeException("InValid Interval. Options are 'Weekly','Daily','Monthly'");
						}
					} else {
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
