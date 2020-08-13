package com.leyou.oss.ali.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ali.oss")
public class AliOssProperty {
    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    @Value("${aliyun.oss.endpoint}")
    private  String endpoint;

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    @Value("${aliyun.oss.accessKeyId}")
    private  String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private  String accessKeySecret;

    @Value("${aliyun.oss.bucket}")
    private  String bucket; // 请填写您的 bucketname 。

    private  String host = null;

    public String getEndpoint() {
        return endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String getBucket() {
        return bucket;
    }

    public String getHost() {
        if (host == null){
            synchronized (this) {
                if (host == null){
                    host = "https://" + this.getBucket() + "." + this.getEndpoint(); // host的格式为 bucketname.endpoint

                }
            }
        }
        return host;
    }
}
