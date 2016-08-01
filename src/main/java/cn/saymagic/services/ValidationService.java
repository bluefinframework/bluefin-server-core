package cn.saymagic.services;

import cn.saymagic.error.GlobalError;
import cn.saymagic.util.TextUtil;
import org.springframework.stereotype.Service;
import rx.Observable;

/**
 * Created by saymagic on 16/5/21.
 */
@Service
public class ValidationService {

    public boolean isFileNameLegal(String name) {
        if (TextUtil.isEmpty(name)) {
            return false;
        }
        if (!name.endsWith(".apk")) {
            return false;
        }
        return true;
    }
}
