package cn.saymagic.services.hook;

import cn.saymagic.entity.BaseWrapper;
import cn.saymagic.util.TextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by saymagic on 16/6/10.
 */
@Service("mUploadApkHookService")
public class UploadApkHookService extends BaseHook {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate mRestTemplate;

    @Autowired
    private ExecutorService mHookExecutor;

    @Value("#{'${hook.upload.apk.urls}'.split(',')}")
    private List<String> mHookUrls = new ArrayList<String>();

    @Override
    public void onHook(Object object) {
        if (mHookUrls == null || mHookUrls.isEmpty() || (mHookUrls.size() == 1 && TextUtil.isEmpty(mHookUrls.get(0)))) {
            return;
        }
        BaseWrapper wrapper = (BaseWrapper) object;
        mHookExecutor.execute(() -> {
                String post = wrapper.toJson().toString();
                for (String s : mHookUrls) {
                    ResponseEntity result = null;
                    try {
                        result = mRestTemplate.postForEntity(s, post, Object.class);
                    } catch (Exception e) {
                        logger.error("post error", e);
                    }
                    logger.info("Hook: url=" + s + "; post=" + post + "; result=" + (result == null ? "" : result.toString()));
                }
        });
    }
}