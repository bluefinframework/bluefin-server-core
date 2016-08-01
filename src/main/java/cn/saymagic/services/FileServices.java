package cn.saymagic.services;

import cn.saymagic.config.FileConfig;
import cn.saymagic.entity.BaseWrapper;
import cn.saymagic.error.GlobalError;
import cn.saymagic.rx.transformer.LastModifiedTransformer;
import cn.saymagic.services.hook.UploadApkHookService;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rx.Observable;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static cn.saymagic.config.FileConfig.*;

/**
 * Created by saymagic on 16/5/20.
 *
 * Service for getting file info.
 */
@Service
public class FileServices {

    @Autowired
    private ValidationService mValidationService;

    @Autowired
    private ApkPraserService mApkPraserService;

    @Autowired
    private UploadApkHookService mUploadApkHookService;

    public Observable<File> getFileInputStream(String product, String identify) throws FileNotFoundException {
        return Observable.<File>create(subscriber -> {
            String path = getPath() + File.separator + product + File.separator + identify;
            File file = new File(path);
            if (file.isDirectory()) {
                File[] files = file.listFiles((dir, aim) -> aim.endsWith(".apk"));
                if (files != null && files.length > 0) {
                    List<File> fileList = Arrays.asList((files));
                    fileList.forEach(subscriber::onNext);
                }
            } else {
                subscriber.onError(new GlobalError(GlobalError.APP_FILE_NOT_FOUND));
            }
            subscriber.onCompleted();
        }).compose(LastModifiedTransformer.worker());
    }


    public Observable handleNewMapping(MultipartFile multipartFile, BaseWrapper wrapper) {
        Observable observable = Observable.<MultipartFile, BaseWrapper, BaseWrapper>zip(
                Observable.just(multipartFile),
                Observable.just(wrapper),
                (file, info) -> {
                    String name = file.getOriginalFilename().replace(" ", "-");
                    File dstFile = new File(FileConfig.getPath() + info.getBasePath() + name);
                    try {
                        file.transferTo(dstFile);
                        info.setMapping(dstFile);
                        return info;
                    } catch (IOException e) {
                        Observable.error(new GlobalError(GlobalError.IO_ERROR, e));
                    }
                    return info;
                });
        return observable;
    }

    public Observable<BaseWrapper> handleNewFile(MultipartFile multipartFile) {
        Observable observable = Observable.just(multipartFile)
                .concatMap(file -> {
                    String name = file.getOriginalFilename();
                    if (mValidationService.isFileNameLegal(name)) {
                        File aimFile = generateRandomFile(name);
                        try {
                            file.transferTo(aimFile);
                            return Observable.just(aimFile);
                        } catch (IOException e) {
                            return Observable.error(new GlobalError(GlobalError.IO_ERROR, e));
                        }
                    }
                    return Observable.error(new GlobalError(GlobalError.PARAMETER_ERROR));
                }).flatMap((file -> {
                    try {
                        return Observable.just(mApkPraserService.extractInfoFromFile(file));
                    } catch (IOException e) {
                        return Observable.error(new GlobalError(GlobalError.IO_ERROR, e));
                    }
                }))
                .concatMap(baseWrapper -> {
                    try {
                        copyTmpFile(baseWrapper);
                        writeInfoToFile(baseWrapper);
                        return Observable.just(baseWrapper);
                    } catch (IOException e) {
                        return Observable.error(new GlobalError(GlobalError.IO_ERROR, e));
                    } catch (JSONException e) {
                        return Observable.error(new GlobalError(GlobalError.IO_ERROR, e));
                    }
                }).doOnNext((baseWrapper -> {
                    mUploadApkHookService.onHook(baseWrapper);
                }));
        return observable;
    }


    public Observable<File> getMappingFile(String app, String identify) {
        return  Observable.<File>create(subscriber -> {
            String path = getPath() + File.separator + app + File.separator + identify + File.separator;
            File file = new File(path);
            if (file.exists() || file.isDirectory()) {
                File files[] = file.listFiles((mappingFile, mappingName) -> mappingName.endsWith(".txt"));
                if (files.length > 0) {
                    List<File> fileList = Arrays.asList(files);
                    fileList.forEach(subscriber::onNext);
                } else {
                    subscriber.onError(new GlobalError(GlobalError.MAPPING_FILE_NOT_FOUND));
                }
            } else {
                subscriber.onError(new GlobalError(GlobalError.MAPPING_FILE_NOT_FOUND));
            }
            subscriber.onCompleted();
        }).<File>compose(LastModifiedTransformer.worker());
    }

    public Observable<String> getInfoFile(String infoPath) {
        return Observable.just(infoPath)
                .map(File::new)
                .filter(file -> file.exists() && file.isFile())
                .first()
                .flatMap((file) -> {
                    try {
                        return Observable.just(FileUtils.readFileToString(file));
                    } catch (IOException e) {
                        return Observable.error(new GlobalError(GlobalError.PACKAGE_NOT_FOUND, e));
                    }
                });
    }

    public Observable<String> getInfoFile(String app, String identify) {
        String path = getPath() + File.separator + app + File.separator + identify + File.separator + getInfoname();
        return getInfoFile(path);
    }

    private void writeInfoToFile(BaseWrapper baseWrapper) throws IOException, JSONException {
        FileUtils.write(new File(getPath() + baseWrapper.getAimInfoPath(getInfoname())), baseWrapper.toJson());
    }

    private void copyTmpFile(BaseWrapper baseWrapper) throws IOException {
        File tmpFile = baseWrapper.getFile();
        File aimFile = new File(getPath() + baseWrapper.getAimFilePath());
        FileUtils.copyFile(tmpFile, aimFile);
    }

    public synchronized File generateRandomFile(String originName) {
        try {
            FileUtils.forceMkdir(new File(getTmp()));
        } catch (IOException e) {
        }
        long time = System.currentTimeMillis();
        String fileName = getTmp() + File.separator + time + "-" + originName;
        File file =  new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private File getFile(String product, String identify) {
        String path = getPath() + File.separator + product + File.separator + identify;
        File file = new File(path);
        if (file.isDirectory()) {
            String[] list = file.list((dir, aim) -> aim.endsWith(".apk"));
            if (list != null && list.length > 0) {
                String aim = list[0];
                return new File(path + File.separator + aim);
            }
        }
        return null;
    }

}
