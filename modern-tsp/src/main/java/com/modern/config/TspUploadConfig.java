package com.modern.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author：tzh
 * @Package：com.modern.config
 * @Project：tsp
 * @name：TspUploadConfig
 * @Date：2024/11/1 15:11
 * @Filename：TspUploadConfig
 */
@Component
@ConfigurationProperties(prefix = "imgs")
@Data
public class TspUploadConfig {
    private String salePath;

    private String upPlatePath;

    private String auditPath;

    private String userPath;
}
