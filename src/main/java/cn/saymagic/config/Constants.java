package cn.saymagic.config;

import org.apache.commons.lang.SystemUtils;

import java.io.File;

/**
 * Created by saymagic on 16/5/21.
 */
public class Constants {

    public static final String PROGUARD_PATH = File.separator +  "tools"
            + File.separator + "proguard"
            + File.separator + "bin"
            + File.separator + (SystemUtils.IS_OS_WINDOWS ? "retrace.bat" : "retrace.sh");

    public static final String SERVER_LOG_NAME = "bluefin.log";

    public static final String INFO_FILE_NAME = "info.json";

    public static final String SERVER_PORT = "2556";

}
