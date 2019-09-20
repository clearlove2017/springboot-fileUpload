package com.ty.file.config;

import com.aliyun.oss.OSS;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: maven
 * @date: 2019/9/18 10:25
 * @description:
 */
@Configuration
@ConditionalOnClass({OSS.class})
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {
    private final OssProperties ossProperties;

    public OssAutoConfiguration(final OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    //暂时注释不使用aliyun oss，需要等服务上线再使用
    /*@Bean
    public OssClientFactoryBean ossClientFactoryBean() {
        final OssClientFactoryBean factoryBean = new OssClientFactoryBean();
        factoryBean.setEndpoint(this.ossProperties.getEndpoint());
        factoryBean.setAccessKeyId(this.ossProperties.getAccessKeyId());
        factoryBean.setAccessKeySecret(this.ossProperties.getAccessKeySecret());
        return factoryBean;
    }*/
}
