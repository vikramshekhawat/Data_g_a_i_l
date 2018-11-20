package com.gail.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.sun.el.parser.ParseException;

public class UTCDate {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.YYYY:HH.mm.ss");
	private static final SimpleDateFormat IST_FORMAT = new SimpleDateFormat("hh:mm a");

	public static Date getCurrentUTCDate() {
		return getCurrentUTCTime().getTime();
	}

	public static Date getFutureDate(int days) {
		Calendar local = Calendar.getInstance(Locale.getDefault());
		int offset = local.getTimeZone().getOffset(local.getTimeInMillis());
		GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		long oneDayMillis = 86400000;
		long noOfDay = days;
		long result = oneDayMillis * noOfDay;
		utc.setTimeInMillis(local.getTimeInMillis() + result);
		utc.add(Calendar.MILLISECOND, -offset);
		return utc.getTime();
	}

	public static GregorianCalendar getCurrentUTCTime() {
		Calendar local = Calendar.getInstance(Locale.getDefault());
		int offset = local.getTimeZone().getOffset(local.getTimeInMillis());
		GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		utc.setTimeInMillis(local.getTimeInMillis());
		utc.add(Calendar.MILLISECOND, -offset);
		return utc;
	}

	public static String getTimeInISTFormat(Date utcTime) throws ParseException {
		GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		utc.setTimeInMillis(utcTime.getTime());
		utc.add(Calendar.MILLISECOND, 19800000);
		return IST_FORMAT.format(utc.getTime());
	}

	public static String getDateInISTFormat(Date utcTime) throws ParseException {
		GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		utc.setTimeInMillis(utcTime.getTime());
		utc.add(Calendar.MILLISECOND, 19800000);
		return formatDate.format(utc.getTime());

	}

	public static String getFormattedDate(Date date) {
		return format.format(date);
	}

	public static int getMemberAge(Date dateOfBirth) {
		Calendar dob = Calendar.getInstance();
		dob.setTime(dateOfBirth);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		return age;
	}

	public static Date getDateInISTFormats(Date utcTime) throws ParseException {
		GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		utc.setTimeInMillis(utcTime.getTime());
		utc.add(Calendar.MILLISECOND, 19800000);
		return utc.getTime();

	}

}
