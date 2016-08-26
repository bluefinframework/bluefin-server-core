package net.dongliu.apk.parser;

import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.Icon;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Locale;

/**
 * Main method for parser apk
 *
 * @author Liu Dong {@literal <im@dongliu.net>}
 */
public class Main {
    public static void main(String[] args) throws IOException, CertificateException {
        String apkFile = args[0];
        try (ApkParser parser = new ApkParser("/Users/ywd/Downloads/b/Curriculum.apk_1.0.apk")) {
            parser.setPreferredLocale(Locale.getDefault());
            ApkMeta apkMeta =parser.getApkMeta();
            Icon icon = parser.getIconFile();

        }
    }
}
