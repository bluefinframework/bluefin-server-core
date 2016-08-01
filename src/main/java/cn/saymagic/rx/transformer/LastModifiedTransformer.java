package cn.saymagic.rx.transformer;

import rx.Observable;

import java.io.File;

/**
 * Created by saymagic on 16/6/7.
 */
public class LastModifiedTransformer implements Observable.Transformer<File, File> {

    private static LastModifiedTransformer lastModifiedTransformer = new LastModifiedTransformer() ;

    public static LastModifiedTransformer worker() {
        return lastModifiedTransformer;
    }

    @Override
    public Observable<File> call(Observable<File> fileObservable) {
        return fileObservable.reduce(new File(""),
                (file1, file2) -> file1.lastModified() > file2.lastModified() ? file1 : file2);
    }

}
