package cn.saymagic.entity;

import net.erdfelt.android.apk.AndroidApk;

import java.io.File;
import java.io.IOException;

/**
 * Created by saymagic on 16/5/23.
 */
public class Apk extends BaseWrapper {

    public Apk(File file) throws IOException, NullPointerException, IllegalArgumentException {
        super(file);
        AndroidApk mBaseApk = new AndroidApk(file);
        mVersionCode = mBaseApk.getAppVersionCode();
        mMinVersion = mBaseApk.getMinSdkVersion();
        mVersionName = mBaseApk.getAppVersion();
        mPackageName = mBaseApk.getPackageName();
        mTargetVersion = mBaseApk.getTargetSdkVersion();
        mUpdateInfo = mBaseApk.getUpdateInfo();
        mExtData = mBaseApk.getExtData();
        mIdentify = mBaseApk.getIdentify();
    }

}
