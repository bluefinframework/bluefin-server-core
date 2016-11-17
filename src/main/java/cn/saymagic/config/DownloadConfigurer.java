package cn.saymagic.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class DownloadConfigurer
        extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${filestore.path}")
    private String mStorePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (mStorePath == null) {
            super.addResourceHandlers(registry);
            return;
        }
        mStorePath = mStorePath.endsWith(File.separator) ? mStorePath : mStorePath + File.separator;
        registry.addResourceHandler("/api/v1/download/**").addResourceLocations(String.format("file:%s", mStorePath));
        logger.info("addResourceHandlers mStorePath = " + String.format("file:%s", mStorePath));
        super.addResourceHandlers(registry);
    }

}