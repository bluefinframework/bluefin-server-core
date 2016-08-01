package cn.saymagic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by saymagic on 16/6/20.
 */
@Configuration
public class UploadApkHookConfig {

    @Bean
    public ExecutorService getExecutorService(){
        return Executors.newSingleThreadExecutor();
    }

}
