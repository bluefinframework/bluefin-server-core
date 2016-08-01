package cn.saymagic.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.io.File;

/**
 * Created by saymagic on 16/6/9.
 */
@Service
public class ServerlLogService {

    @Value("${logging.file}")
    private String mServerLogFilePath;

    public Observable<File> getServerLogFile() {
        return Observable.just(mServerLogFilePath).map(File::new);
    }
}
