package com.project.utils;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils {
	/**
	 * Date time formatter
	 */
	public static final String DATEMONTH_FM = "yyyy-MM",
			DATE_FM = "yyyy-MM-dd", DATETIME_FM = "yyyy-MM-dd HH:mm:ss",
			TIME_FM = "HH:mm:ss", DATE_MIGU = "yyyyMMddHH",
			DATETIME_DETAIL = "yyyyMMddHHmmss";

	
	public static java.sql.Date stringToDate(String dateStr) {
		return utilDateToSql(parse(DATE_FM, dateStr));
	}
	
	public static Timestamp stringToTime(String dateStr) {
		return new java.sql.Timestamp(parse(TIME_FM, dateStr).getTime());
	}

	// 获得当前日期与本周一相差的天数
	public static int getMondayPlus(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	// 获得当前周- 周一的日期
	public static java.sql.Date getCurrentMonday(String dateStr) {
		Date date = stringToDate(dateStr);
		int mondayPlus = getMondayPlus(date);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return stringToDate(preMonday);
	}

	// 获得当前周- 周日 的日期
	public static java.sql.Date getPreviousSunday(String dateStr) {
		Date date = stringToDate(dateStr);
		int mondayPlus = getMondayPlus(date);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return stringToDate(preMonday);
	}

	// 获得当前月--开始日期
	public static java.sql.Date getMinMonthDate(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(dateStr));
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			return stringToDate(dateFormat.format(calendar.getTime()));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获得当前月--结束日期
	public static java.sql.Date getMaxMonthDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return stringToDate(dateFormat.format(calendar.getTime()));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static java.sql.Date utilDateToSql(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 格式化参数时间
	 * 
	 * @param pattern - ""
	 * @param d -
	 *            Date
	 * @return the formatted date-time string
	 * @see SimpleDateFormat
	 */
	public static Date format(String pattern, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return stringToDate(dateFormat.format(date));
	}

	/**
	 * 转换日期字符为Date对象
	 * 
	 * @param pattern -
	 *            the pattern of the string
	 * @param strDateTime -
	 *            the string to be parsed
	 * @return A Date parsed from the string. In case of error, returns null.
	 */
	public static Date parse(String pattern, String strDateTime) {
		Date date = null;
		if (strDateTime == null || pattern == null)
			return null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			formatter.setLenient(false);
			date = formatter.parse(strDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 转换日期字符为Date对象，格式为
	 * 
	 * @param strDateTime -
	 *            the string to be parsed
	 */
	public static Date parseDateTime(String strDateTime) {
		if (strDateTime == null)
			return null;
		return parse(DATETIME_FM, strDateTime);
	}

	/**
	 * 指定在当前时间下增加秒/分/时/天/月/年
	 * 
	 * @param calendarType(Calendar.类型)
	 * @param skipLen :
	 *            累加长度(可负)
	 * @return
	 */
	public static Date skip(int calendarType, int skipLen) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendarType, skipLen);
		return calendar.getTime();
	}

	/**
	 * 判断年份是否为闰年
	 * 
	 * @param year
	 *            年份
	 */
	public static boolean isLeapYear(int year) {
		if ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 4) == 0)
				&& ((year % 400) == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取今年
	 */
	public static int getYear() {
		return get(Calendar.YEAR);
	}

	/**
	 * 获取今月
	 */
	public static int getMonth() {
		return get(Calendar.MONTH);
	}

	/**
	 * 获取今日
	 */
	public static int getDay() {
		return get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour() {
		return get(Calendar.HOUR_OF_DAY);
	}

	public static int getMin() {
		return get(Calendar.MINUTE);
	}

	public static int getSec() {
		return get(Calendar.SECOND);
	}

	public static int get(int calendarType) {
		Calendar c = Calendar.getInstance();
		return c.get(calendarType);
	}
	
	public static int getYYYYMMDD(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int created = year * 10000 + mon * 100 + day;
		return created;
	}
	
	public static int getYYYYMM(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH) + 1;
		int created = year * 100 + mon;
		return created;
	}

	/**
	 * 返回year年份month月份的天数
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public static int getMonthDays(int month, int year) {
		if ((isLeapYear(year) == true) && (month == 2)) {
			return 29;
		} else if ((isLeapYear(year) == false) && (month == 2)) {
			return 28;
		}
		if ((month == 1) || (month == 3) || (month == 5) || (month == 7)
				|| (month == 8) || (month == 10) || (month == 12)) {
			return 31;
		}
		return 30;
	}

	/**
	 * 获取当前时间
	 */
	public static Date getCurDate() {
		return format(DATE_FM, new Date());
	}

	/**
	 * 加减日期
	 * 
	 * @param date
	 * @param days
	 *
	 */
	public static String getAddDate(String date, int days) {
		Calendar calendar = Calendar.getInstance();
		String[] calen = date.split("-");
		int year = Integer.parseInt(calen[0]);
		int month = Integer.parseInt(calen[1]);
		int day = Integer.parseInt(calen[2]);
		calendar.set(year, month - 1, day);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return DateUtils.getDate(calendar.get(Calendar.YEAR)) + "-"
				+ DateUtils.getDate(calendar.get(Calendar.MONTH) + 1) + "-"
				+ DateUtils.getDate(calendar.get(Calendar.DAY_OF_MONTH));
	}

	public static String getDate(int i) {
		return i < 10 ? "0" + i : i + "";
	}

	// 获取日期的月初
	public static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (1 - index));
		return calendar.getTime();
	}

	// 获取日期的月末
	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (-index));
		return calendar.getTime();
	}

	// 获取日期的明天
	public static Date getNext(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 将字符串中的特殊字符转进行转义
	 * 
	 * @param s
	 * @return
	 */
	public static String stringToJson(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			// case '\"':
			// sb.append("\\\"");
			// break;
			// case '\\': //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
			// sb.append("\\\\");
			// break;
			case '/':
				sb.append("\\/");
				break;
			case '\b': // 退格
				sb.append("\\b");
				break;
			case '\f': // 走纸换页
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n"); // 换行
				break;
			case '\r': // 回车
				sb.append("\\r");
				break;
			case '\t': // 横向跳格
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 根据月份获得天数
	 * 
	 * @param month
	 *            月份
	 * @param year
	 *            年份
	 * @return
	 */
	public static int getDayNumberByMonth(int month, int year) {
		int day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			boolean b = isLeapYear(year);
			if (b) {
				day = 29;
			} else {
				day = 28;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


	public static void main(String[] args) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// System.out.println(getTrunDecimals("1.23535"));

	}
}
