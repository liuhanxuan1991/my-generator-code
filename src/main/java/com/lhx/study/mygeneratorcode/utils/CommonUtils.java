package com.lhx.study.mygeneratorcode.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *公共工具类
 *
 */
public final class CommonUtils {

    private static final String regxpForHtml = "<([^>]*)>";
    private static final Pattern REG_INTEGER = Pattern.compile("(\\+|\\-)?\\d+");
    private static final Pattern IS_LOWCASE = Pattern.compile("[a-z]+");
    private static final Pattern IS_UPPERCASE = Pattern.compile("[A-Z]+");
    private static final char[] CHAR_ARRAY = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^(13[0-9]|14[5|7]|15[0-3|5-9]|18[0|2|3|5-9])\\d{8}$");
    private static final Pattern MAIL_PATTERN = Pattern.compile("^\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b$");



    /**
     * 首字母大写
     * @param content
     * @return
     */
    public static String firstCharToUpperCase(String content) {
        if (!isEmpty(content)) {
            String tou = content.substring(0, 1);
            String wei = content.substring(1);
            content = tou.toUpperCase() + wei;
        }

        return content;
    }

    /**
     * 首字母小写
     * @param content
     * @return
     */
    public static String firstCharToLowerCase(String content) {
        if (!isEmpty(content)) {
            String tou = content.substring(0, 1);
            String wei = content.substring(1);
            content = tou.toLowerCase() + wei;
        }

        return content;
    }



    public static boolean isDigit(final char sch) {
        int temp = sch & 255;
        return temp >= 48 && temp <= 57;
    }

    public static boolean isDigit(final String string) {
        boolean digit = false;
        if (isNotBlank(string)) {
            digit = REG_INTEGER.matcher(string).matches();
        }

        return digit;
    }

    public static boolean isLowerCase(final char sch) {
        int temp = sch & 255;
        return temp >= 97 && temp <= 122;
    }

    public static boolean isLowerCase(final String string) {
        return isNotBlank(string) ? IS_LOWCASE.matcher(string).matches() : false;
    }

    public static boolean isUppercase(final char sch) {
        int temp = sch & 255;
        return temp >= 65 && temp <= 90;
    }

    public static boolean isUppercases(final String string) {
        return isNotBlank(string) ? IS_UPPERCASE.matcher(string).matches() : false;
    }

    public static String toHexString(final String sourceStr) {
        StringBuffer strBuff = new StringBuffer("");
        if (isNotBlank(sourceStr)) {
            char[] var2 = sourceStr.toCharArray();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                char ch = var2[var4];
                strBuff.append(toHexString(ch));
            }
        }

        return strBuff.toString();
    }

    public static String bytesToHexString(final byte[] bytes) {
        StringBuilder sBuilder = new StringBuilder("");
        if (bytes != null && bytes.length > 0) {
            byte[] var2 = bytes;
            int var3 = bytes.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte byitem = var2[var4];
                int tmpv = byitem & 255;
                String thv = Integer.toHexString(tmpv);
                if (thv.length() < 2) {
                    sBuilder.append(0);
                }

                sBuilder.append(thv);
            }
        }

        return sBuilder.toString();
    }

    private static String toUnsignedString(final long nums, final int shifts) {
        long tmpNums = nums;
        char[] chs = new char[64];
        int charPosition = 64;
        int radix = 1 << shifts;
        int value = radix - 1;

        do {
            --charPosition;
            chs[charPosition] = CHAR_ARRAY[(int)(tmpNums & (long)value)];
            tmpNums >>>= shifts;
        } while(0L < tmpNums);

        return new String(chs, charPosition, 64 - charPosition);
    }

    public static String toHexString(final int nums) {
        return toUnsignedString((long)nums, 4);
    }

    public static String toHexString(final long nums) {
        return toUnsignedString(nums, 4);
    }

    public static String toOctalString(final int nums) {
        return toUnsignedString((long)nums, 3);
    }

    public static String toOctalString(final long nums) {
        return toUnsignedString(nums, 3);
    }

    public static String toBinaryString(final int nums) {
        return toUnsignedString((long)nums, 1);
    }

    public static String toBinaryString(final long nums) {
        return toUnsignedString(nums, 1);
    }

    public static boolean isEmpty(final Object object) {
        return null == object;
    }

    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

    public static boolean isBlank(final String string) {
        return isEmpty(string) || "".equals(string.trim());
    }

    public static boolean isNotBlank(final String string) {
        return isNotEmpty(string) && !"".equals(string.trim());
    }

    public static String toUTF8(final String string) {
        if (isBlank(string)) {
            throw new IllegalArgumentException("The param can not be null.");
        } else {
            try {
                return new String(string.getBytes("UTF-8"), "ISO8859-1");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return string;
            }
        }
    }

    public static String toGBK(final String string) {
        if (isBlank(string)) {
            throw new IllegalArgumentException("The param can not be null.");
        } else {
            try {
                return new String(string.getBytes("GBK"), "ISO8859-1");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return string;
            }
        }
    }

    public static boolean isLegalMobile(final String mobile) {
        return isNotBlank(mobile) ? MOBILE_PATTERN.matcher(mobile).find() : false;
    }

    public static boolean isLegalMail(final String mail) {
        return isNotBlank(mail) ? MAIL_PATTERN.matcher(mail.toUpperCase(Locale.getDefault())).find() : false;
    }

    public static String byteArrayStreamToString(ByteArrayOutputStream baos) {
        return byteArrayStreamToString(baos, "UTF-8");
    }

    public static String byteArrayStreamToString(ByteArrayOutputStream baos, String charset) {
        if (null == baos) {
            throw new IllegalArgumentException("The param baos can not be null.");
        } else {
            try {
                return baos.toString("UTF-8");
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static String excNullToString(String string) {
        return excNullToString(string, "");
    }

    public static String excNullToString(String string, String added) {
        if (isEmpty(string)) {
            string = added;
        }

        return string;
    }

    public static Object excNullToObject(Object obj) {
        return excNullToObject(obj, new Object());
    }

    public static Object excNullToObject(Object obj, Object added) {
        if (isEmpty(obj)) {
            obj = added;
        }

        return obj;
    }

    public static String objectToString(Object obj) {
        return objectToString(obj, (String)null);
    }

    public static String objectToString(Object obj, String added) {
        String result = added;
        if (obj != null) {
            result = String.valueOf(obj);
        }

        return result;
    }

    public static int stringToInt(String string) {
        return stringToInt(string, 0);
    }

    public static int stringToInt(String string, int added) {
        boolean var2 = false;

        int result;
        try {
            result = Integer.parseInt(string);
        } catch (Exception var4) {
            result = added;
        }

        return result;
    }

    public static int objectToInt(Object obj) {
        return objectToInt(obj, 0);
    }

    public static int objectToInt(Object obj, int added) {
        if (obj instanceof Integer) {
            return (Integer)obj;
        } else if (obj instanceof Float) {
            return ((Float)obj).intValue();
        } else {
            return obj instanceof Double ? ((Double)obj).intValue() : stringToInt(objectToString(obj), added);
        }
    }

    public static long stringToLong(String string) {
        return stringToLong(string, 0L);
    }

    public static long stringToLong(String string, long added) {
        long result = 0L;

        try {
            result = Long.parseLong(string);
        } catch (Exception var6) {
            result = added;
        }

        return result;
    }

    public static long objectToLong(Object obj) {
        return objectToLong(obj, 0L);
    }

    public static long objectToLong(Object obj, long added) {
        long result = 0L;

        try {
            result = Long.parseLong(obj.toString());
        } catch (Exception var6) {
            result = added;
        }

        return result;
    }

    public static float stringToFloat(String string) {
        return stringToFloat(string, 0.0F);
    }

    public static float stringToFloat(String string, float added) {
        float result = 0.0F;

        try {
            result = Float.parseFloat(string);
        } catch (Exception var4) {
            result = added;
        }

        return result;
    }

    public static float objectToFloat(Object obj) {
        return objectToFloat(obj, 0.0F);
    }

    public static float objectToFloat(Object obj, float added) {
        float result = 0.0F;

        try {
            result = Float.parseFloat(obj.toString());
        } catch (Exception var4) {
            result = added;
        }

        return result;
    }

    public static double stringToDouble(String string) {
        return stringToDouble(string, 0.0D);
    }

    public static double stringToDouble(String string, double added) {
        double result = 0.0D;

        try {
            result = Double.parseDouble(string);
        } catch (Exception var6) {
            result = added;
        }

        return result;
    }

    public static double objectToDouble(Object obj) {
        return objectToDouble(obj, 0.0D);
    }

    public static double objectToDouble(Object obj, double added) {
        double result = 0.0D;

        try {
            result = Double.parseDouble(obj.toString());
        } catch (Exception var6) {
            result = added;
        }

        return result;
    }


    public static String stringUncode(String param) {
        if (param != null && !param.trim().equals("")) {
            try {
                param = URLDecoder.decode(param, "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
            }
        }

        return param;
    }

    public static String stringEncode(String param) {
        if (param != null && !param.trim().equals("")) {
            try {
                param = URLEncoder.encode(param, "utf-8");
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
            }
        }

        return param;
    }

    public static String format(String resource, String... target) {
        if (resource == null) {
            throw new IllegalArgumentException("parameter is null");
        } else {
            if (target != null && target.length > 0) {
                int i = 0;

                for(int k = target.length; i < k; ++i) {
                    resource = resource.replace("{" + i + "}", target[i]);
                }
            }

            return resource;
        }
    }

    public static long getTimeUnix() {
        return (new Date()).getTime();
    }

    public static String filterHtml(String str) {
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();

        for(boolean result1 = matcher.find(); result1; result1 = matcher.find()) {
            matcher.appendReplacement(sb, "");
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String replaceName(String name) {
        char[] chars = new char[]{name.charAt(0)};
        String temp = new String(chars);
        return chars[0] >= 'A' && chars[0] <= 'Z' ? name.replaceFirst(temp, temp.toLowerCase()) : name;
    }
}
