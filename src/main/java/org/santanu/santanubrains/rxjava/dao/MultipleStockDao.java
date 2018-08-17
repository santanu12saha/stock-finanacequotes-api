package org.santanu.santanubrains.rxjava.dao;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.utility.DateComparison;
import org.santanu.santanubrains.rxjava.utility.SpecifiedInterval;
import org.santanu.santanubrains.rxjava.utility.StringToCalender;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MultipleStockDao {

	public static Map<String, Stock> fetch(MultipleStockQuery multipleStockQuery) {

		try {
			return YahooFinance.get(multipleStockQuery.getStockSymbols()
					.toArray(new String[multipleStockQuery.getStockSymbols().size()]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, Stock> fetchMultipleStockWithHistoricalData(MultipleStockQuery multipleStockQuery,
			boolean includeHistorical) {

		try {
			return YahooFinance.get(multipleStockQuery.getStockSymbols()
					.toArray(new String[multipleStockQuery.getStockSymbols().size()]), includeHistorical);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Map<String, Stock> fetchMultipleStockByFromDate(MultipleStockQuery multipleStockQuery,
			String fromDate) {

		try {
			if (DateComparison.fromLesserCurrent(fromDate)) {

				return YahooFinance.get(
						multipleStockQuery.getStockSymbols()
								.toArray(new String[multipleStockQuery.getStockSymbols().size()]),
						StringToCalender.convert(fromDate));
			} else {
				throw new RuntimeException(
						"From Date: " + fromDate + " should be less than Current Date : " + new Date());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Map<String, Stock> fetchMultipleStockByInterval(MultipleStockQuery multipleStockQuery,
			String interval) {

		try {
			if (SpecifiedInterval.interval(interval) != null) {
				return YahooFinance.get(
						multipleStockQuery.getStockSymbols()
								.toArray(new String[multipleStockQuery.getStockSymbols().size()]),
						SpecifiedInterval.interval(interval));
			} else {
				throw new RuntimeException("InValid Interval. Options are 'Weekly','Daily','Monthly'");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, Stock> fetchMultipleStockByFromToDate(MultipleStockQuery multipleStockQuery,
			String fromDate, String toDate) {

		try {
			if (DateComparison.fromDateLesserToDate(fromDate, toDate)) {

				if (DateComparison.fromLesserCurrent(fromDate)) {

					if (DateComparison.toDateLesserCurrent(toDate)) {

						return YahooFinance.get(
								multipleStockQuery.getStockSymbols()
										.toArray(new String[multipleStockQuery.getStockSymbols().size()]),
								StringToCalender.convert(fromDate), StringToCalender.convert(toDate));
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

	public static Map<String, Stock> fetchMultipleStockByFromInterval(MultipleStockQuery multipleStockQuery,
			String fromDate, String interval) {

		try {

			if (DateComparison.fromLesserCurrent(fromDate)) {

				if (SpecifiedInterval.interval(interval) != null) {

					return YahooFinance.get(
							multipleStockQuery.getStockSymbols()
									.toArray(new String[multipleStockQuery.getStockSymbols().size()]),
							StringToCalender.convert(fromDate), SpecifiedInterval.interval(interval));
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

	public static Map<String, Stock> fetchMultipleStockByFromToDateInterval(MultipleStockQuery multipleStockQuery,
			String fromDate, String toDate, String interval) {

		try {
			if (DateComparison.fromDateLesserToDate(fromDate, toDate)) {

				if (DateComparison.fromLesserCurrent(fromDate)) {

					if (DateComparison.toDateLesserCurrent(toDate)) {

						if (SpecifiedInterval.interval(interval) != null) {

							return YahooFinance.get(
									multipleStockQuery.getStockSymbols()
											.toArray(new String[multipleStockQuery.getStockSymbols().size()]),
									StringToCalender.convert(fromDate), StringToCalender.convert(toDate),
									SpecifiedInterval.interval(interval));
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
