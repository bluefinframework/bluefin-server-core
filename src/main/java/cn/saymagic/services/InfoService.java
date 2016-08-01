package cn.saymagic.services;

import cn.saymagic.config.FileConfig;
import cn.saymagic.error.GlobalError;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cn.saymagic.config.FileConfig.getInfoname;
import static cn.saymagic.config.FileConfig.getPath;

/**
 * Created by saymagic on 16/6/6.
 */
@Service
public class InfoService {

    @Autowired
    private FileServices mFileService;

    public Observable<String> getLastestInfo(String app) {
        return getLastestVersion(app)
                .flatMap(identify -> mFileService.getInfoFile(app, identify));
    }

    public Observable<String> getLastestVersion( String app) {
        String path = FileConfig.getPath() + File.separator + app;
        return Observable.just(path)
                .map(File::new)
                .flatMap(file -> {
                    if (file.exists() && file.isDirectory()) {
                        String[] versiondDrectoryArray = file.list();
                        if (versiondDrectoryArray != null && versiondDrectoryArray.length > 0) {
                            List<String> versiondDrectoryList = Arrays.asList(versiondDrectoryArray);
                            Collections.sort(Arrays.asList(versiondDrectoryArray));
                            return Observable.just(versiondDrectoryList.get(versiondDrectoryList.size() -1));
                        }
                    }
                    return Observable.error(new GlobalError(GlobalError.PACKAGE_NOT_FOUND));
                });
    }

    public Observable<JSONArray> listAllFileInfos(String app) {
        return Observable.<File>create(subscriber -> {
            String path = getPath() + File.separator + app;
            File file = new File(path);
            if (file.exists() || file.isDirectory()) {
                subscriber.onNext(file);
            } else {
                subscriber.onError(new GlobalError(GlobalError.PACKAGE_NOT_FOUND));
            }
            subscriber.onCompleted();
        }).map(file -> file.listFiles())
                .flatMap(files -> Observable.from(files))
                .filter(file -> file.isDirectory())
                .flatMap(file -> mFileService.getInfoFile(file.getAbsolutePath() + File.separator + getInfoname()))
                .reduce(new JSONArray(), (jsonArray, s) -> {
                            try {
                                return jsonArray.put(new JSONObject(s));
                            } catch (JSONException e) {
                                return jsonArray;
                            }
                        }
                );
    }



    public Observable<String> getAppInfo(String app, String identify) {
        return mFileService.getInfoFile(app, identify);
    }

}
