package cn.saymagic.entity;

import net.dongliu.apk.parser.ApkParser;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by saymagic on 16/5/23.
 */
public class Apk extends BaseWrapper {

    public Apk(File file) throws IOException, NullPointerException, IllegalArgumentException {
        super(file);
        ApkParser parser = new ApkParser(file);
        parser.setPreferredLocale(Locale.getDefault());
        ApkMeta apkMeta =parser.getApkMeta();
        mVersionCode = String.valueOf(apkMeta.getVersionCode());
        mMinVersion = apkMeta.getMinSdkVersion();
        mVersionName = apkMeta.getVersionName();
        mPackageName = apkMeta.getPackageName();
        mTargetVersion = apkMeta.getTargetSdkVersion();
        mUpdateInfo = apkMeta.getUpdateInfo();
        mExtData = apkMeta.getExtData();
        mIdentify = apkMeta.getIdentify();
        mName = apkMeta.getName();
        mIcon = parser.getIconFile().getData();
    }

}
