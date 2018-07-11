package com.mysimple.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateTool {
	
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";  
    public static final String YM = "yyyyMM";  
    public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";  
    public static final String SHORT_DATE_FORMAT = "MM-dd";  
    public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-mm-dd hh24:mi:ss";  
    public static final String DATE_FORMAT = "yyyy-MM-dd";  
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";  
    public static final String DATE_ALL = "yyyyMMddHHmmssS";  
    public static final String DATETIME_FORMAT_CHINESE = "yyyy年MM月dd日HH时mm分ss秒";  
    public static final String NORMAL_DATE_FORMAT_CHINESE = "yyyy年MM月dd日";  
    
    private static final String[] pattern = new String[]{"yyyyMMddHHmmss"};  
  
    public static Long strDateToNum(String paramString) throws Exception {  
        if (paramString == null)  
            return null;  
        String[] arrayOfString = null;  
        String str = "";  
        if (paramString.indexOf("-") >= 0) {  
            arrayOfString = paramString.split("-");  
            for (int i = 0; i < arrayOfString.length; ++i)  
                str = str + arrayOfString[i];  
            return Long.valueOf(Long.parseLong(str));  
        }  
        return Long.valueOf(Long.parseLong(paramString));  
    }  
  
    public static Long strDateToNum1(String paramString) throws Exception {  
        if (paramString == null)  
            return null;  
        String[] arrayOfString = null;  
        String str = "";  
        if (paramString.indexOf("-") >= 0) {  
            arrayOfString = paramString.split("-");  
            for (int i = 0; i < arrayOfString.length; ++i)  
                if (arrayOfString[i].length() == 1)  
                    str = str + "0" + arrayOfString[i];  
                else  
                    str = str + arrayOfString[i];  
            return Long.valueOf(Long.parseLong(str));  
        }  
        return Long.valueOf(Long.parseLong(paramString));  
    }  
  
    public static String numDateToStr(Long paramLong) {  
        if (paramLong == null)  
            return null;  
        String str = null;  
        str = paramLong.toString().substring(0, 4) + "-"  
                + paramLong.toString().substring(4, 6) + "-"  
                + paramLong.toString().substring(6, 8);  
        return str;  
    }  
  
  
    public static java.util.Date stringToDate(String paramString1,  
            String paramString2) throws Exception {  
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(  
                paramString2);  
        localSimpleDateFormat.setLenient(false);  
        try {  
            return localSimpleDateFormat.parse(paramString1);  
        } catch (ParseException localParseException) {  
            throw new Exception("解析日期字符串时出错！");  
        }  
    }  
  
    public static String dateToString(java.util.Date paramDate,  
            String paramString) {  
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(  
                paramString);  
        localSimpleDateFormat.setLenient(false);  
        return localSimpleDateFormat.format(paramDate);  
    }  
  
    public static java.util.Date compactStringToDate(String paramString)  
            throws Exception {  
        return stringToDate(paramString, "yyyyMMdd");  
    }  
  
    public static String dateToCompactString(java.util.Date paramDate) {  
        return dateToString(paramDate, "yyyyMMdd");  
    }  
  
    public static String dateToNormalString(java.util.Date paramDate) {  
        return dateToString(paramDate, "yyyy-MM-dd");  
    }  
    
    public static String dateToStringToChinese(java.util.Date paramDate) {  
    	return dateToString(paramDate, NORMAL_DATE_FORMAT_CHINESE);  
    }  
  
    public static String compactStringDateToNormal(String paramString)  
            throws Exception {  
        return dateToNormalString(compactStringToDate(paramString));  
    }  
  
    public static int getDaysBetween(java.util.Date paramDate1,  
            java.util.Date paramDate2) throws Exception {  
        Calendar localCalendar1 = Calendar.getInstance();  
        Calendar localCalendar2 = Calendar.getInstance();  
        localCalendar1.setTime(paramDate1);  
        localCalendar2.setTime(paramDate2);  
        if (localCalendar1.after(localCalendar2))  
            throw new Exception("起始日期小于终止日期!");  
        int i = localCalendar2.get(6) - localCalendar1.get(6);  
        int j = localCalendar2.get(1);  
        if (localCalendar1.get(1) != j) {  
            localCalendar1 = (Calendar) localCalendar1.clone();  
            do {  
                i += localCalendar1.getActualMaximum(6);  
                localCalendar1.add(1, 1);  
            } while (localCalendar1.get(1) != j);  
        }  
        return i;  
    }  
  
    public static java.util.Date addDays(java.util.Date paramDate, int paramInt)  
            throws Exception {  
        Calendar localCalendar = Calendar.getInstance();  
        localCalendar.setTime(paramDate);  
        int i = localCalendar.get(6);  
        localCalendar.set(6, i + paramInt);  
        return localCalendar.getTime();  
    }  
  
    public static java.util.Date addDays(String paramString1,  
            String paramString2, int paramInt) throws Exception {  
        Calendar localCalendar = Calendar.getInstance();  
        java.util.Date localDate = stringToDate(paramString1, paramString2);  
        localCalendar.setTime(localDate);  
        int i = localCalendar.get(6);  
        localCalendar.set(6, i + paramInt);  
        return localCalendar.getTime();  
    }  
  
    public static java.sql.Date getSqlDate(java.util.Date paramDate)  
            throws Exception {  
        java.sql.Date localDate = new java.sql.Date(paramDate.getTime());  
        return localDate;  
    }  
  
    public static String formatDate(java.util.Date paramDate) {  
        if (paramDate == null)  
            return null;  
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        localSimpleDateFormat.setLenient(false);  
        return localSimpleDateFormat.format(paramDate);  
    } 
   
    /**
     * 中文格式 yyyy年MM月dd日 HH:mm
     * @param paramDate
     * @return
     */
    public static String formatDateChinese(java.util.Date paramDate) {  
        if (paramDate == null)  
            return null;  
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT_CHINESE);  
        localSimpleDateFormat.setLenient(false);  
        return localSimpleDateFormat.format(paramDate);  
    } 
    
    
  
    public static String formatDateTime(java.util.Date paramDate) {  
        if (paramDate == null)  
            return null;  
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        localSimpleDateFormat.setLenient(false);  
        return localSimpleDateFormat.format(paramDate);  
    }  
  
    public static java.util.Date parseDate(String paramString)  
            throws Exception {  
        if ((paramString == null) || (paramString.trim().equals("")))  
            return null;  
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        localSimpleDateFormat.setLenient(false);  
        try {  
            return localSimpleDateFormat.parse(paramString);  
        } catch (ParseException localParseException) {  
            throw new Exception("日期解析出错！", localParseException);  
        }  
    }  
  
    public static java.util.Date parseDateTime(String paramString)  
            throws Exception {  
        if ((paramString == null) || (paramString.trim().equals("")))  
            return null;  
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");  
        localSimpleDateFormat.setLenient(false);  
        try {  
        	if(paramString.length() > 12){
        		return localSimpleDateFormat.parse(paramString);  
        	}else{
        		return localSimpleDateFormat1.parse(paramString);  
        	}
            
        } catch (ParseException localParseException) {  
            throw new Exception("时间解析异常！", localParseException);  
        }  
    }  
  
    public static Integer getYM(String paramString) throws Exception {  
        if (paramString == null)  
            return null;  
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        localSimpleDateFormat.setLenient(false);  
        java.util.Date localDate;  
        try {  
            localDate = localSimpleDateFormat.parse(paramString);  
        } catch (ParseException localParseException) {  
            throw new Exception("时间解析异常！", localParseException);  
        }  
        return getYM(localDate);  
    }  
  
    public static Integer getYM(java.util.Date paramDate) {  
        if (paramDate == null)  
            return null;  
        Calendar localCalendar = Calendar.getInstance();  
        localCalendar.setTime(paramDate);  
        int i = localCalendar.get(1);  
        int j = localCalendar.get(2) + 1;  
        return new Integer(i * 100 + j);  
    }  
  
    public static int addMonths(int paramInt1, int paramInt2) {  
        Calendar localCalendar = Calendar.getInstance();  
        localCalendar.set(1, paramInt1 / 100);  
        localCalendar.set(2, paramInt1 % 100 - 1);  
        localCalendar.set(5, 1);  
        localCalendar.add(2, paramInt2);  
        return getYM(localCalendar.getTime()).intValue();  
    }  
  
    public static java.util.Date addMonths(java.util.Date paramDate,  
            int paramInt) {  
        Calendar localCalendar = Calendar.getInstance();  
        localCalendar.setTime(paramDate);  
        localCalendar.add(2, paramInt);  
        return localCalendar.getTime();  
    }  
  
    public static int monthsBetween(int paramInt1, int paramInt2) {  
        int i = paramInt2 / 100 * 12 + paramInt2 % 100  
                - (paramInt1 / 100 * 12 + paramInt1 % 100);  
        return i;  
    }  
  
    public static int monthsBetween(java.util.Date paramDate1,  
            java.util.Date paramDate2) {  
        return monthsBetween(getYM(paramDate1).intValue(), getYM(paramDate2).intValue());  
    }  
  
    public static String getChineseDate(Calendar paramCalendar) {  
        int i = paramCalendar.get(1);  
        int j = paramCalendar.get(2);  
        int k = paramCalendar.get(5);  
        StringBuffer localStringBuffer = new StringBuffer();  
        localStringBuffer.append(i);  
        localStringBuffer.append("年");  
        localStringBuffer.append(j + 1);  
        localStringBuffer.append("月");  
        localStringBuffer.append(k);  
        localStringBuffer.append("日");  
        return localStringBuffer.toString();  
    }  
  
    public static String getChineseDate(String dateStr) throws Exception {  
    	Date date = DateTool.parseDate(dateStr);
    	Calendar paramCalendar = Calendar.getInstance();
    	paramCalendar.setTime(date);
    	return getChineseDate(paramCalendar);
    }
    public static String getChineseWeekday(Calendar paramCalendar) {  
        switch (paramCalendar.get(7)) {  
        case 2:  
            return "周一";  
        case 3:  
            return "周二";  
        case 4:  
            return "周三";  
        case 5:  
            return "周四";  
        case 6:  
            return "周五";  
        case 7:  
            return "周六";  
        case 1:  
            return "周日";  
        }  
        return "未知";  
    } 
    
    public static String getChineseWeekday(Date paramDate) {  
    	Calendar paramCalendar = Calendar.getInstance();
    	paramCalendar.setTime(paramDate);
        switch (paramCalendar.get(7)) {  
        case 2:  
            return "周一";  
        case 3:  
            return "周二";  
        case 4:  
            return "周三";  
        case 5:  
            return "周四";  
        case 6:  
            return "周五";  
        case 7:  
            return "周六";  
        case 1:  
            return "周日";  
        }  
        return "未知";  
    } 
    
    public static boolean isWeekend(Date date){  
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
        int week=cal.get(Calendar.DAY_OF_WEEK)-1;  
        if(week==0){//0代表周日，6代表周六  
            return true;  
        }  
        return false;  
    } 
    
    public static boolean isWeekend(Calendar cal){  
        int week=cal.get(Calendar.DAY_OF_WEEK)-1;  
        if(week==0){//0代表周日，6代表周六  
            return true;  
        }  
        return false;  
    }  
    /**
     * 
     * @param date
     * @param days
     * @param set 0：时间戳0 1：最末时间戳 23:59:59.999
     * @return
     */
    public static java.util.Date getDateByAddDays(java.util.Date date, Integer days, Integer set){
    	Calendar cd = Calendar.getInstance();  
    	cd.setTime(date);
    	
    	switch (set) {
	        case 0:
	        	cd.set(Calendar.HOUR_OF_DAY, 0);
	        	cd.set(Calendar.MINUTE, 0);
	        	cd.set(Calendar.SECOND, 0);
	        	cd.set(Calendar.MILLISECOND, 0);
	        	break;
	        case 1:
	        	cd.set(Calendar.HOUR_OF_DAY, 23);
	        	cd.set(Calendar.MINUTE, 59);
	        	cd.set(Calendar.SECOND, 59);
	        	cd.set(Calendar.MILLISECOND, 999);
	        	break;
	        default:
	        	
        }
    	if(days!=null){
    		cd.add(Calendar.DATE, days);
    	}
    	return cd.getTime();
    }
    
    
    public static Long dateStrToTimeStap(String date){
    	if(date == null){
    		return null;
    	}
    	date = date.trim();
    	if(!date.contains(":")){
    		date += " 00:00:00.000";
    	}
    	Timestamp ts = Timestamp.valueOf(date);
    	return ts.getTime();
    }
    
    public static Long nowTimeStap(){
    	Date d = new Date();
    	return d.getTime();
    }
    
    /**
     * 
     * @param dateStr
     * @param days
     * @param set 0：时间戳0 1：最末时间戳 23:59:59.999
     * @return
     * @throws ParseException 
     */
    public static java.util.Date getDateByAddDays(String dateStr, String format, Integer days, Integer set) throws ParseException{
    	Calendar cd = Calendar.getInstance();  
    	SimpleDateFormat sdf = new SimpleDateFormat(format); 
    	cd.setTime(sdf.parse(dateStr));
    	
    	switch (set) {
	        case 0:
	        	cd.set(Calendar.HOUR_OF_DAY, 0);
	        	cd.set(Calendar.MINUTE, 0);
	        	cd.set(Calendar.SECOND, 0);
	        	cd.set(Calendar.MILLISECOND, 0);
	        	break;
	        case 1:
	        	cd.set(Calendar.HOUR_OF_DAY, 23);
	        	cd.set(Calendar.MINUTE, 59);
	        	cd.set(Calendar.SECOND, 59);
	        	cd.set(Calendar.MILLISECOND, 999);
	        	break;
	        default:
	        	
        }
    	if(days!=null){
    		cd.add(Calendar.DATE, days);
    	}
    	return cd.getTime();
    }
    
    
    /**
     * 将时间段分隔为周， 列表里面每个日期都是每周开始的日期
     * @return
     */
    public static List<Date> splitTwoDateInWeek(Date startDate, Date endDate) throws Exception {
    	startDate = DateTool.parseDate(DateTool.formatDate(startDate));
    	endDate = DateTool.parseDate(DateTool.formatDate(endDate));
    	List<Date> list = new ArrayList<>();
    	list.add(startDate);
    	int i=1;
    	while(DateTool.addDays(startDate,7*i).getTime() < endDate.getTime()){
    		list.add(DateTool.addDays(startDate,7*i));
    		i++;
    	}
    	list.add(DateTool.addDays(endDate, 1));
    	return list;
    }
    
    public static List<Date> splitTwoDateInMonth(Date startDate, Date endDate) throws Exception {
    	startDate = DateTool.parseDate(DateTool.formatDate(startDate));
    	endDate = DateTool.parseDate(DateTool.formatDate(endDate));
    	List<Date> list = new ArrayList<>();
    	list.add(startDate);
    	Date newDate = startDate;
    	do{
    		newDate = DateTool.parseDate(DateTool.formatDate(DateTool.addMonths(newDate, 1)).substring(0, 7)+"-01");
    		if(newDate.getTime() < endDate.getTime()){
    			list.add(newDate);
    		}
    	}while(newDate.getTime() < endDate.getTime());
    	list.add(DateTool.addDays(endDate, 1));
    	return list;
    }
    
    public static String formatWeekTitle(Date startDate, Date endDate) throws Exception{
    	if(DateTool.getDaysBetween(startDate,endDate) == 7){
    		endDate =  DateTool.addDays(endDate , -1);
    	}
    	String start = DateTool.formatDate(startDate);
    	String end = DateTool.formatDate(endDate);
    	String s = start.substring(5).replace("-", "/")+"-"+end.substring(5).replace("-", "/");
    	return s;
    }
    
    public static String formatWeekTitle(Date startDate) throws Exception{
    	Date endDate =  DateTool.addDays(startDate , 6);
    	String start = DateTool.formatDate(startDate);
    	String end = DateTool.formatDate(endDate);
    	String s = start.substring(5).replace("-", "/")+"-"+end.substring(5).replace("-", "/");
    	return s;
    }
    
    public static String formatMonthTitle(Date startDate) throws Exception{
    	String start = DateTool.formatDate(startDate);
    	String s = start.substring(5,7);
    	if(s.startsWith("0")){
    		return s.substring(1)+"月";
    	}else{
    		return s+"月";
    	}
    }
    
    public static Date getTodayStart(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
    	return cal.getTime();
    }
    
    public static Date getTodayEnd(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
    	return cal.getTime();
    }
    
    //判断是否周一
    public static boolean checkWeekenStart(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_WEEK) == 2;
    }
    //判断是否月一号
    public static boolean checkMonthStart(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_MONTH) == 1;
    }
    //判断是否季度初
    public static boolean checkSeasonStart(Date date) throws Exception {
    	Calendar cal1 = Calendar.getInstance();
    	cal1.setTime(date);
    	Calendar cal2 = Calendar.getInstance();
    	cal2.setTime(getSeasonStart(date));
    	boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                        .get(Calendar.DAY_OF_MONTH);

    	return isSameDate;
    }
    //判断是否年第一天
    public static boolean cheakYearStart(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.DAY_OF_YEAR) == 1;
    }
    
    public static Date getWeekenEnd(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期
        //String data1 = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        //String data2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        //System.out.println(data2);
        //return data1 + "-" + data2;
        return cal.getTime();
    }
    
    public static Date getMonthEnd(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	cal.add(Calendar.MONTH, 1);
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
    	Date lastDayOfMonth = cal.getTime();
    	return lastDayOfMonth;
    }
    
    public static Date getSeasonStart(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);  
        int month = getQuarterInMonth(cal.get(Calendar.MONTH), true);  
        cal.set(Calendar.MONTH, month);  
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
        return cal.getTime();
    }
    
    //季度最后一天
    public static Date getSeasonEnd(Date date) throws Exception {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);  
        int month = getQuarterInMonth(cal.get(Calendar.MONTH), false);  
        cal.set(Calendar.MONTH, month + 1);  
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    public static Date getYearEnd(Date date) throws Exception {
    	 Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         int year = cal.get(Calendar.YEAR);
         cal.clear();
         cal.set(Calendar.YEAR, year);
         cal.roll(Calendar.DAY_OF_YEAR, -1);
         cal.set(Calendar.HOUR_OF_DAY, 23);
         cal.set(Calendar.MINUTE, 59);
         cal.set(Calendar.SECOND, 59);
         cal.set(Calendar.MILLISECOND, 999);
         Date currYearLast = cal.getTime();
         return currYearLast;
    }
    
    private static int getQuarterInMonth(int month, boolean isQuarterStart) {  
        int months[] = { 1, 4, 7, 10 };  
        if (!isQuarterStart) {  
            months = new int[] { 3, 6, 9, 12 };  
        }  
        if (month >= 2 && month <= 4)  
            return months[0];  
        else if (month >= 5 && month <= 7)  
            return months[1];  
        else if (month >= 8 && month <= 10)  
            return months[2];  
        else  
            return months[3];  
    }  
}
