package org.santanu.santanubrains.rxjava.utility;

import java.util.Calendar;
import java.util.Date;

public class DateComparison {

	private static Calendar calFromDate = null;
	private static Calendar calToDate = null;
	private static Calendar currentDate = null;

	public static boolean fromLesserCurrent(String fromDate) {

		calFromDate = StringToCalender.convert(fromDate);
		currentDate = Calendar.getInstance();
		currentDate.setTime(new Date());
		return calFromDate.before(currentDate);

	}

	public static boolean toDateLesserCurrent(String toDate) {

		calToDate = StringToCalender.convert(toDate);
		currentDate = Calendar.getInstance();
		currentDate.setTime(new Date());
		return calToDate.before(currentDate);

	}

	public static boolean fromDateLesserToDate(String fromDate, String toDate) {
		
		calFromDate = StringToCalender.convert(fromDate);
		calToDate = StringToCalender.convert(toDate);
		System.out.println(calFromDate.before(calToDate));
		return calFromDate.before(calToDate);
	}

}
