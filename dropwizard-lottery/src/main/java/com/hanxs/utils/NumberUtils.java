package com.hanxs.utils;

public class NumberUtils {
    private NumberUtils(){}
    private static NumberUtils instance;
    public static NumberUtils getInstance(){
        if (null == instance)
            instance = new NumberUtils();
        return instance;
    }
}
