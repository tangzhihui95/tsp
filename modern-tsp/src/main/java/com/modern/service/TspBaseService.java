package com.modern.service;

import com.modern.common.utils.file.FileUploadUtils;
import com.modern.config.TspUploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author：tzh
 * @Package：com.modern.service
 * @Project：tsp
 * @name：TspBaseService
 * @Date：2024/8/28 16:37
 * @Filename：TspBaseService
 */
@Service
public class TspBaseService {
    private static final Logger log = LoggerFactory.getLogger(TspBaseService.class);
    @Resource
    private TspUploadConfig config;

    public String upload(MultipartFile file, Integer type) {
        log.info("车辆二级型号上传图片---------------file={},type={}",file,type);
        try {
            String property = System.getProperty("os.name");
            boolean isLinux = (property != null && property.startsWith("Linux"));
            String path = "";
            switch (type.intValue()) {
                case 1:
                    path = FileUploadUtils.upload(isLinux ? config.getSalePath() : "D://", file);
                    break;
                case 2:
                    path = FileUploadUtils.upload(isLinux ? config.getUpPlatePath() : "D://", file);
                    break;
                case 3:
                    path = FileUploadUtils.upload(isLinux ? config.getAuditPath() : "D://", file);
                    break;
                case 4:
                    path = FileUploadUtils.upload(isLinux ? config.getUserPath() : "D://", file);
                    break;
                default:
                    throw new RuntimeException("上传失败");
            }
            if (isLinux) {
                path.replace("/home/mode/profile/upload", "/image/");
            } else {
                path = path.replace("D://", "/image/");
            }
            log.info("车辆二级型号上传图片结束---------------path={}",path);
            return path;
        } catch (Throwable $ex) {
            try {
                throw $ex;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
