package com.lhx.study.mygeneratorcode.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat(DATE_FORMAT);
    public static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");


    public static Date parse(String dateStr) {
        return parse(dateStr, (String)null);
    }

    public static Date parse(String dateStr, String pattern) {
        Date date = null;
        if (null != dateStr) {
            try {
                if (dateStr.length() < 12) {
                    dateStr = dateStr + " 00:00:00";
                }

                if (pattern != null) {
                    SimpleDateFormat sim = new SimpleDateFormat(pattern);
                    date = sim.parse(dateStr);
                } else {
                    date = SDF_DATETIME.parse(dateStr);
                }
            } catch (ParseException var4) {
//                log.error("时间格式转换失败！", var4);
            }
        } else {
//            log.error("字符串为空：dateStr=" + dateStr);
        }

        return date;
    }

    public static String format(Date date) {
        return format(date, (String)null);
    }

    public static String format(Date date, String pattern) {
        String dateStr = null;
        if (date != null) {
            try {
                if (pattern != null) {
                    SimpleDateFormat sim = new SimpleDateFormat(pattern);
                    dateStr = sim.format(date);
                } else {
                    dateStr = SDF_DATETIME.format(date);
                }
            } catch (Exception var4) {
//                log.error("时间格式化字符串失败！", var4);
            }
        } else {
//            log.error("Date参数为空：date=" + date);
        }

        return dateStr;
    }

    public static Timestamp parseToTimestamp(String dateStr, String pattern) {
        Timestamp tmp = null;
        if (!CommonUtils.isEmpty(dateStr) && !CommonUtils.isEmpty(pattern)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            try {
                tmp = new Timestamp(sdf.parse(dateStr).getTime());
            } catch (ParseException var5) {
                var5.printStackTrace();
            }

            return tmp;
        } else {
            throw new IllegalArgumentException("The parameter is null.");
        }
    }

    public static Timestamp parseToTimestamp(String dateStr) {
        return parseToTimestamp(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static int getAgeByDate(Date birthDate) {
        Date day = new Date();
        int age = day.getYear() - birthDate.getYear();
        birthDate.setYear(birthDate.getYear() + age);
        age = birthDate.getTime() - day.getTime() > 0L ? age - 1 : age;
        return age;
    }

    public static int getWeekByDate() {
        return getWeekByDate(new Date());
    }

    public static int getWeekByDate(Date date) {
        SimpleDateFormat sim = new SimpleDateFormat("w");
        String wk = sim.format(date);
        return CommonUtils.stringToInt(wk, -1);
    }

    public static int getAgeByDateStr(String birthDateStr) {
        Date birthDate = parse(birthDateStr, "yyyy-MM-dd");
        return getAgeByDate(birthDate);
    }

    public static Date getBeforeOrAfterDateByDayNumber(int dayNumber) {
        return getBeforeOrAfterDateByDayNumber(new Date(), dayNumber);
    }

    public static Date getBeforeOrAfterDateByDayNumber(Date date, int dayNumber) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, dayNumber);
        return c.getTime();
    }

    public static Date getBeforeOrAfterDateByMonthNumber(int monthNumber) {
        return getBeforeOrAfterDateByMonthNumber(new Date(), monthNumber);
    }

    public static Date getBeforeOrAfterDateByMonthNumber(Date date, int monthNumber) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, monthNumber);
        return c.getTime();
    }

    public static boolean compareDateOrTime(Date date, int genre) {
        return compareDateOrTime(date, new Date(), genre);
    }

    public static boolean compareDateOrTime(Date date1, Date date2, int genre) {
        boolean bool = false;
        long times1 = date1.getTime();
        long times2 = date2.getTime();
        if (genre == 1) {
            bool = times1 < times2;
        } else if (genre == 2) {
            bool = times1 > times2;
        } else if (genre == 3) {
            bool = times1 <= times2;
        } else if (genre == 4) {
            bool = times1 >= times2;
        } else if (genre == 5) {
            bool = times1 == times2;
        }

        return bool;
    }

    public static boolean compareDateOrTime(String date, int genre) {
        Date day = new Date();
        if (date != null && date.length() < 12) {
            day = parse(SDF_DATE.format(day));
        }

        return compareDateOrTime(parse(date), day, genre);
    }

    public static boolean compareDateOrTime(String date1, String date2, int genre) {
        return compareDateOrTime(parse(date1), parse(date2), genre);
    }

    public static String getLastMonDay() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int maxDay = cal.getActualMaximum(5);
        cal.set(5, maxDay);
        return format(cal.getTime(), "yyyy-MM-dd");
    }

    public static String getFirstOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(1, year);
        c.set(3, week);
        c.set(7, 1);
        c.setFirstDayOfWeek(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    public static String getLastOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(1, year);
        c.set(3, week);
        c.set(7, 1);
        c.setFirstDayOfWeek(1);
        c.set(7, c.getFirstDayOfWeek() + 6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    public static void main(String[] args) {
        System.out.println(compareDateOrTime((String)"2013-04-02", 1));
        Date day = new Date();
        Calendar cc = Calendar.getInstance();
        cc.setTime(day);
        cc.set(2, cc.get(2) + 8);
        cc.set(5, 28);
        System.out.println(format(day));
        System.out.println(format(cc.getTime()));
        int i = (new Double(Math.ceil(2.001D))).intValue();
        System.out.println(i);
        Calendar xx = Calendar.getInstance();
        System.out.println(getFirstOfWeek(xx.get(1), xx.get(3)));
        System.out.println(getLastOfWeek(xx.get(1), xx.get(3)));
    }
}
