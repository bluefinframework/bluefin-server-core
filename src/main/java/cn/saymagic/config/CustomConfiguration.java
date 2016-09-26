package cn.saymagic.config;

import cn.saymagic.generater.Base64UrlSafeGenerater;
import cn.saymagic.generater.IdGenerater;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by saymagic on 16/8/31.
 */
@Configuration
public class CustomConfiguration {

    @Bean
    public IdGenerater idGenerator() {
        return new Base64UrlSafeGenerater();
    }

}
