package cn.saymagic.services;

import cn.saymagic.entity.Apk;
import cn.saymagic.entity.BaseWrapper;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by saymagic on 16/5/21.
 */
@Service("mApkPraiseService")
public class ApkPraserService implements IPraserService {

    @Autowired
    private IdService mIdService;

    @Override
    public BaseWrapper extractInfoFromFile(File file) throws IOException {
        Apk apk = new Apk(file);
        String packageEncryption = mIdService.generate(apk.getPackageName());
        String identityEncryption = mIdService.generate(apk.getIdentify());
        String time = String.valueOf(System.currentTimeMillis());
        apk.setDownloadUrl("/api/v1/download/" + packageEncryption + "/" + identityEncryption + "/" + time + ".apk");
        apk.setIconUrl("/api/v1/download/" + packageEncryption + "/" + identityEncryption + "/" + time + ".png");
        apk.setPackageEncryption(packageEncryption);
        apk.setIdentityEncryption(identityEncryption);
        apk.setUploadTime(time);
        return apk;
    }


}
