package com.ty.file.config;

import com.ty.file.factory.FtpClientFactory;
import com.ty.file.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: maven
 * @date: 2019/9/17 15:22
 * @description: bean配置类
 */
@Configuration
@EnableConfigurationProperties(FtpClientProperties.class)
public class FtpClientConfigure {
    private FtpClientProperties ftpClientProperties;

    @Autowired
    public void setFtpClientProperties(FtpClientProperties ftpClientProperties) {
        this.ftpClientProperties = ftpClientProperties;
    }

    @Bean
    public FtpClientFactory getFtpClientFactory() {
        return new FtpClientFactory(ftpClientProperties);
    }

    @Bean
    public FtpUtil getFtpUtil() {
        return new FtpUtil(getFtpClientFactory());
    }
}
