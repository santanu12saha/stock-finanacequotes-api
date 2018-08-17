package org.santanu.santanubrains.rxjava.utility;


import yahoofinance.histquotes.Interval;

public class SpecifiedInterval {

	public static Interval interval(String userInterval) {
		if (userInterval.isEmpty()) {
			throw new RuntimeException(
					"Interval must be specified. Options are 'Weekly','Daily','Monthly'");
		} else {

			switch (userInterval) {
			case "DAILY":
				return Interval.DAILY;
			case "WEEKLY":
				return Interval.WEEKLY;

			case "MONTHLY":
				return Interval.MONTHLY;
			}

		}
		return null;
	}
}
