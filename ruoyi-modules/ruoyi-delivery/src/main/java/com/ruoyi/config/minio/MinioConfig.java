package com.ruoyi.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/2/29 11:17
 */
@Component
@ConfigurationProperties(prefix = "mz.minio")
@Data
public class MinioConfig {
    private String endpoint;
    private String bucket;

}
