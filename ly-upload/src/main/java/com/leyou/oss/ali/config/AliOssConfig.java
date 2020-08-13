package com.leyou.oss.ali.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.leyou.oss.ali.property.AliOssProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliOssConfig {

    @Autowired
    private AliOssProperty property;

    @Bean
    public  OSSClient createOssClient(){
        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        return (OSSClient) new OSSClientBuilder().build(property.getEndpoint(), property.getAccessKeyId(), property.getAccessKeySecret());
    }

}
