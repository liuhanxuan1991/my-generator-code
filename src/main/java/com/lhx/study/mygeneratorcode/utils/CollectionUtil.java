//package com.lhx.study.mygeneratorcode.utils;
//
//import java.util.*;
//
//public class CollectionUtil {
//    public CollectionUtil() {
//    }
//
//    public static boolean isBlank(Map map) {
//        return null == map || map.isEmpty();
//    }
//
//    public static boolean isNotBlank(Map map) {
//        if (null != map && !map.isEmpty()) {
//            return true;
//        } else {
//            return !isBlank(map);
//        }
//    }
//
//    public static boolean isBlank(Collection<?> cols) {
//        return null == cols || cols.isEmpty();
//    }
//
//    public static boolean isNotBlank(Collection<?> cols) {
//        return null != cols && !cols.isEmpty();
//    }
//
//    public static <E> E getLast(List<E> list) {
//        return isNotBlank((Collection)list) ? list.get(list.size() - 1) : null;
//    }
//
//    public static <E> E getFirst(List<E> list) {
//        return isNotBlank((Collection)list) ? list.get(0) : null;
//    }
//
//    public static boolean isBlank(Object[] arr) {
//        return arr.length == 0 || null == arr;
//    }
//
//    public static boolean isNotBlank(Object[] arr) {
//        return arr.length != 0 && null != arr;
//    }
//
//    public static String[] distinct(String[] arr) {
//        Set<String> set = new HashSet();
//
//        for(int i = 0; i < arr.length; ++i) {
//            set.add(arr[i]);
//        }
//
//        return (String[])set.toArray(new String[set.size()]);
//    }
//}
