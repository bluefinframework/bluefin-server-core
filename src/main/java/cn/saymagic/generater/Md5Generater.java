package cn.saymagic.generater;

import org.apache.tomcat.util.security.MD5Encoder;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by saymagic on 16/11/17.
 */
public class Md5Generater implements IdGenerater {

    @Override
    public String generate(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(origin.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return origin;
        }
    }

    @Override
    public String restore(String cipher) {
        return null;
    }
}
