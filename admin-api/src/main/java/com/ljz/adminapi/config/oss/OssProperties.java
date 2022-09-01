package com.ljz.adminapi.config.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p></p>
 *
 * @Author : ljz
 * @Date: 2022/8/31  15:08
 */

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class OssProperties {
    private String endPoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
}
