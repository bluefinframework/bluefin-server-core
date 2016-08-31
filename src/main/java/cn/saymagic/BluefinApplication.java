package cn.saymagic;

import cn.saymagic.config.Constants;
import cn.saymagic.util.TextUtil;
import org.apache.commons.lang.SystemUtils;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import sun.misc.Unsafe;

import java.io.File;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;


@Configuration
@ComponentScan
@EnableAutoConfiguration
@Cacheable
public class BluefinApplication {

    private static final String BLUEFIN_HOME = SystemUtils.getUserHome().getAbsolutePath() + File.separator + ".bluefin";
    private static final String ANDROID_HOME = System.getenv("ANDROID_HOME");
    private static final String BLUEFIN_BASE_URL = System.getenv("BLUEFIN_BASE_URL");
    private static final String BLUEFIN_SERVER_HOST = System.getenv("BLUEFIN_SERVER_HOST");
    private static final String BLUEFIN_SERVER_PORT = System.getenv("BLUEFIN_SERVER_PORT");
    private static final String BLUEFIN_FILE_STORE_PATH = System.getenv("BLUEFIN_FILE_STORE_PATH");
    private static final String BLUEFIN_FILE_TMP_PATH = System.getenv("BLUEFIN_FILE_TMP_PATH");
    private static final String BLUEFIN_HOOK_URLS = System.getenv("BLUEFIN_HOOK_URLS");

    private static final Map<String, Object> configDefault = new HashMap<String, Object>() {{
        put("server.address", getLocalHostIP());
        put("server.port", getLocalHostPort());
        put("output.baseurl", getBluefinBaseUrl());
        put("android.sdk.home", getAndroidHome());
        put("hook.upload.apk.urls", getHookUrls());
        put("logging.file", getStorePath() + File.separator + "log" + File.separator + Constants.SERVER_LOG_NAME);
        put("filestore.path", getStorePath());
        put("filestore.tmp", getTmpFile());
        put("filestore.infoname", Constants.INFO_FILE_NAME);
    }};

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject(configDefault);
        System.out.println(jsonObject.toString());
        SpringApplication application = new SpringApplication(BluefinApplication.class);
        application.setDefaultProperties(configDefault);
        application.setShowBanner(false);
        application.run(args);
    }

    public static String getLocalHostIP() {
        if (TextUtil.isNotEmpty(BLUEFIN_SERVER_HOST)) {
            return BLUEFIN_SERVER_HOST;
        }
        String ip;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (Exception ex) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    public static String getLocalHostPort() {
        if (TextUtil.isNotEmpty(BLUEFIN_SERVER_PORT)) {
            return BLUEFIN_SERVER_PORT;
        }
        return Constants.SERVER_PORT;
    }

    public static String getStorePath() {
        if (TextUtil.isEmpty(BLUEFIN_FILE_STORE_PATH)) {
            return BLUEFIN_HOME;
        }
        return BLUEFIN_FILE_STORE_PATH;
    }

    public static String getTmpFile() {
        if (TextUtil.isEmpty(BLUEFIN_FILE_TMP_PATH)) {
            return BLUEFIN_HOME + File.separator + "tmp";
        }
        return BLUEFIN_FILE_TMP_PATH;
    }

    public static String getAndroidHome() {
        if (TextUtil.isNotEmpty(ANDROID_HOME)) {
            return ANDROID_HOME;
        }
        return "";
    }

    public static String getBluefinBaseUrl() {
        if (TextUtil.isNotEmpty(BLUEFIN_BASE_URL)) {
            return BLUEFIN_BASE_URL;
        }
        String ip = getLocalHostIP();
        String port = getLocalHostPort();
        String prefix = (ip.startsWith("http://") || ip.startsWith("https://")) ? "" : "http://";
        String suffix = (TextUtil.isEmpty(port) || "80".equals(port)) ? "" : (":" + port);
        return prefix + ip + suffix;
    }

    public static String getHookUrls() {
        if (TextUtil.isNotEmpty(BLUEFIN_HOOK_URLS)) {
            return BLUEFIN_HOOK_URLS;
        }
        return "";
    }
}
