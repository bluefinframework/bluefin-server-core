package cn.saymagic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by saymagic on 16/6/6.
 */
@Component
@ConfigurationProperties(prefix="filestore")
public class FileConfig {

    private static String path;

    private static String tmp;

    private static String infoname;

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        FileConfig.path = path;
    }

    public static String getTmp() {
        return tmp;
    }

    public static void setTmp(String tmp) {
        FileConfig.tmp = tmp;
    }

    public static String getInfoname() {
        return infoname;
    }

    public static void setInfoname(String infoname) {
        FileConfig.infoname = infoname;
    }
}
