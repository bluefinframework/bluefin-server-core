package cn.saymagic.rx.transformer;


import cn.saymagic.entity.BaseWrapper;
import cn.saymagic.services.FileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rx.Observable;

/**
 * Created by saymagic on 16/6/2.
 */
public class MappingSaveTransformer implements Observable.Transformer<BaseWrapper, BaseWrapper> {

    private FileServices mFileService;

    private MultipartFile mMappingFile;

    public MappingSaveTransformer(MultipartFile mapping, FileServices service) {
        mMappingFile = mapping;
        mFileService = service;
    }

    @Override
    public Observable<BaseWrapper> call(Observable<BaseWrapper> originObservable) {
        if (mMappingFile == null) {
            return originObservable;
        }
        BaseWrapper wrapper = originObservable.toBlocking().first();
        return  mFileService.handleNewMapping(mMappingFile, wrapper);
    }

}
