package cn.saymagic.generater;

import cn.saymagic.util.TextUtil;

import java.util.Base64;

/**
 * Created by saymagic on 16/8/31.
 */
public class Base64UrlSafeGenerater implements IdGenerater {

    @Override
    public String generate(String origin) {
        if (TextUtil.isEmpty(origin)) {
            return "";
        }
        return Base64.getUrlEncoder().withoutPadding().encodeToString(origin.getBytes());
    }

    @Override
    public String restore(String cipher) {
        if (TextUtil.isEmpty(cipher)) {
            return "";
        }
        return new String(Base64.getUrlDecoder().decode(cipher.getBytes()));
    }


}
