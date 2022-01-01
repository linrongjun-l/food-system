package cn.ecut.lrj.aliyun.oss;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties implements InitializingBean {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String policyExpire;
    private String maxSize;
    private String dirPrefix;

    public static String ENDPOINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;
    public static String POLICY_EXPIRE;
    public static String MAX_SIZE;
    public static String DIR_PREFIX;

    //当私有成员被赋值后，此方法自动被调用，从而初始化常量
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        KEY_ID = accessKeyId;
        KEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketName;
        POLICY_EXPIRE = policyExpire;
        MAX_SIZE = maxSize;
        DIR_PREFIX = dirPrefix;
    }
}
