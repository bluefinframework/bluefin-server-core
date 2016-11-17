package cn.saymagic.entity;

import cn.saymagic.generater.IdGenerater;
import cn.saymagic.util.EncryUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by saymagic on 16/5/21.
 */
public abstract class BaseWrapper {

    protected byte[] mIcon;

    protected String mName;

    protected double mSize;

    protected String mQrlink;

    protected long mUpdateTime;

    protected String mVersionCode;

    protected String mVersionName;

    protected String mMinVersion;

    protected String mTargetVersion;

    protected String mPackageName;

    protected String mDownloadUrl;

    protected String mIconUrl;

    protected File mFile;

    protected File mMapping;

    protected String mFileMd5;

    protected String mUpdateInfo;

    protected String mExtData;

    protected String mIdentify;

    protected String mPackageEncryption;

    protected String mIdentityEncryption;

    protected String mUploadTime;

    /**
     * Warn : this is an expensive method
     *
     * @param file
     * @throws IOException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public BaseWrapper(File file) throws IOException, NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(file);
        if (file.isDirectory()) {
            throw new IllegalStateException("file can't be a diretory!");
        }
        mFile = file;
        mFileMd5 = EncryUtil.getMD5(mFile);
        mSize = file.length();
        mUpdateTime = System.currentTimeMillis();
    }

    public double getSize() {
        return mSize;
    }

    public void setSize(double mSize) {
        this.mSize = mSize;
    }

    public String getQrlink() {
        return mQrlink;
    }

    public void setQrlink(String mQrlink) {
        this.mQrlink = mQrlink;
    }

    public long getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(long mUpdateTime) {
        this.mUpdateTime = mUpdateTime;
    }

    public String getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(String mVersionCode) {
        this.mVersionCode = mVersionCode;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String mVersionName) {
        this.mVersionName = mVersionName;
    }

    public String getMinVersion() {
        return mMinVersion;
    }

    public void setMinVersion(String mMinVersion) {
        this.mMinVersion = mMinVersion;
    }

    public String getTargetVersion() {
        return mTargetVersion;
    }

    public void setTargetVersion(String mTargetVersion) {
        this.mTargetVersion = mTargetVersion;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File mFile) {
        this.mFile = mFile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getBasePath() {
        return File.separator + getPackageEncryption() + File.separator
                + getIdentityEncryption() + File.separator;
    }

    public byte[] getIconByte() {
        return mIcon;
    }

    public void setIconByte(byte[] iconByte) {
        this.mIcon = iconByte;
    }

    public String getAimFilePath() {
        return getBasePath() + getUploadTime() + ".apk";
    }

    public String getAimInfoPath(String infoName) {
        return getBasePath() + infoName;
    }

    public String getAimIconPath() {
        return getBasePath() + getUploadTime()  + ".png";
    }

    public File getMapping() {
        return mMapping;
    }

    public void setMapping(File mapping) {
        this.mMapping = mapping;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getMappingPath() {
        File mapping = getMapping();
        if (mapping == null) {
            return "";
        }
        return File.separator + File.separator + getPackageName() + File.separator
                + getVersionCode() + File.separator + mapping.getName();
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String mDownloadUrl) {
        this.mDownloadUrl = mDownloadUrl;
    }


    public String getFileMd5() {
        return mFileMd5;
    }

    public void setFileMd5(String mFileMd5) {
        this.mFileMd5 = mFileMd5;
    }

    public String toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("size", getSize());
        object.put("package", getPackageName());
        object.put("minVersion", getMinVersion());
        object.put("targetVersion", getTargetVersion());
        object.put("versionName", getVersionName());
        object.put("versionCode", getVersionCode());
        object.put("updateTime", getUpdateTime());
        object.put("qrlink", getQrlink());
        object.put("name", getName());
//        object.put("apkPath", getAimFilePath());
//        object.put("mappingPath", getMappingPath());
        object.put("downloadUrl", getDownloadUrl());
        object.put("fileMd5", getFileMd5());
        object.put("updateInfo", getUpdateInfo());
        object.put("extData", getExtData());
        object.put("identity", getIdentify());
        object.put("iconUrl", getIconUrl());
        object.put("uploadTime", getUploadTime());
        object.put("packageEncryption", getPackageEncryption());
        object.put("identityEncryption", getIdentityEncryption());
        return object.toString();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> object = new HashMap<>();
        object.put("size", getSize());
        object.put("name", getName());
        object.put("package", getPackageName());
        object.put("minVersion", getMinVersion());
        object.put("targetVersion", getTargetVersion());
        object.put("versionName", getVersionName());
        object.put("versionCode", getVersionCode());
        object.put("updateTime", getUpdateTime());
        object.put("qrlink", getQrlink());
//        object.put("apkPath", getAimFilePath());
//        object.put("mappingPath", getMappingPath());
        object.put("downloadUrl", getDownloadUrl());
        object.put("fileMd5", getFileMd5());
        object.put("updateInfo", getUpdateInfo());
        object.put("extData", getExtData());
        object.put("identity", getIdentify());
        object.put("iconUrl", getIconUrl());
        object.put("uploadTime", getUploadTime());
        object.put("packageEncryption", getPackageEncryption());
        object.put("identityEncryption", getIdentityEncryption());
        return object;
    }

    public String getExtData() {
        return mExtData;
    }

    public String getUpdateInfo() {
        return mUpdateInfo;
    }

    public String getIdentify() {
        return mIdentify;
    }

    public void setIdentify(String mIdentify) {
        this.mIdentify = mIdentify;
    }

    public String getIdentityEncryption() {
        return mIdentityEncryption;
    }

    public void setIdentityEncryption(String mIdentityEncryption) {
        this.mIdentityEncryption = mIdentityEncryption;
    }

    public String getPackageEncryption() {
        return mPackageEncryption;
    }

    public void setPackageEncryption(String mPackageEncryption) {
        this.mPackageEncryption = mPackageEncryption;
    }

    public String getUploadTime() {
        return mUploadTime;
    }

    public void setUploadTime(String mUploadTime) {
        this.mUploadTime = mUploadTime;
    }
}
