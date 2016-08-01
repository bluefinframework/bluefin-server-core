package cn.saymagic.services;

import cn.saymagic.entity.BaseWrapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by saymagic on 16/5/21.
 */
@Service
public interface IPraserService {

    BaseWrapper extractInfoFromFile(File file) throws IOException;

}
