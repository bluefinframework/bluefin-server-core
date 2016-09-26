package cn.saymagic.services;

import cn.saymagic.config.Constants;
import cn.saymagic.error.GlobalError;
import cn.saymagic.util.ShellUtil;
import cn.saymagic.util.TextUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rx.Observable;

import java.io.File;
import java.io.IOException;

/**
 * Created by saymagic on 16/6/2.
 * <p>
 * Service for converting cipher with proguard.
 */
@Service
public class MappingService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${android.sdk.home:}")
    private String mAndroidSdkHome;

    @Autowired
    private FileServices mFileService;

    public Observable<String> doParsingString(String cipher, String app, String identify) {
        final File[] cipherFile = new File[1];
        final File[] decodeFile = new File[1];
        return Observable.<String, File, File, String>zip(Observable.<String>just(cipher),
                mFileService.getMappingFile(app, identify),
                Observable.create(subscriber -> {
                    String path = null;
                    try {
                        path = getProguard();
                    } catch (GlobalError globalError) {
                        subscriber.onError(globalError);
                        return;
                    }
                    File file = new File(path);
                    if (file == null || !file.exists()) {
                        subscriber.onError(new GlobalError(GlobalError.PROGUARD_FILE_NOT_FOUND));
                        return;
                    } else {
                        subscriber.onNext(file);
                    }
                    subscriber.onCompleted();
                }), (origin, mappingFile, proguardFile) -> {
                    cipherFile[0] = mFileService.generateRandomFile("origin.txt");
                    decodeFile[0] = mFileService.generateRandomFile("decode.txt");
                    try {
                        FileUtils.write(cipherFile[0], origin.toString());
                    } catch (IOException e) {
                        Observable.error(new GlobalError(GlobalError.IO_ERROR, e));
                    }
                    String shell = proguardFile.getAbsolutePath() + " " + "-verbose" + " "
                            + mappingFile.getAbsolutePath() + " "
                            + cipherFile[0].getAbsolutePath() + " > " + decodeFile[0].getAbsolutePath();
                    return shell;
                }).map(ShellUtil::run)
                .map(s -> s.replace("    ", "\n"))
                .doOnCompleted(() -> {
                    if (cipherFile[0] != null) {
                        cipherFile[0].delete();
                    }
                    if (decodeFile[0] != null) {
                        decodeFile[0].delete();
                    }
                });
    }

    public Observable<String> doParsingFile(MultipartFile cipher, String packageName, String version, String type) {
        return null;
    }

    private String getProguard() throws GlobalError {
        if (TextUtil.isEmpty(mAndroidSdkHome)) {
            throw new GlobalError(GlobalError.ANDROID_HOME_IS_NOT_SETUP);
        }
        return mAndroidSdkHome + Constants.PROGUARD_PATH;
    }

}
