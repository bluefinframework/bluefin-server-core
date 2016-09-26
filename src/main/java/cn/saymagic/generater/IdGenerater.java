package cn.saymagic.generater;

/**
 * Created by saymagic on 16/8/31.
 */
public interface IdGenerater {

    String generate(String origin);

    String restore(String cipher);
}

