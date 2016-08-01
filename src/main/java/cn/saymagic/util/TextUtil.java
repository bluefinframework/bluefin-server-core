package cn.saymagic.util;

/**
 * Created by saymagic on 16/5/21.
 */
public class TextUtil {

    public static boolean isEmpty(String s) {
        return s == null ? true : s.equals("");
    }

    public static boolean isNotEmpty(String s) {
        return s == null ? false : !s.equals("");
    }

}

