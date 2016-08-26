package cn.saymagic.services;

import cn.saymagic.entity.Apk;
import cn.saymagic.entity.BaseWrapper;
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
    private FileServices mFileService;

    @Value("${output.baseurl}")
    private String mBaseurl;

    @Override
    public BaseWrapper extractInfoFromFile(File file) throws IOException {
        Apk apk =  new Apk(file);
        apk.setDownloadUrl(mBaseurl + "/api/v1/download/" + apk.getPackageName() + "/" + apk.getIdentify() + ".apk");
        apk.setIconUrl(mBaseurl + "/api/v1/download/" + apk.getPackageName() + "/" + apk.getIdentify() + ".png");
        return apk;
    }


}
