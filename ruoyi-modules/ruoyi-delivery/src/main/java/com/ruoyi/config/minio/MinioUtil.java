package com.ruoyi.config.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 马震
 * @version 1.0
 * @date 2024/3/4 15:53
 */
@Component
public class MinioUtil {


    @Autowired
    MinioConfig minioConfig;


    public HashMap<String, Object> uploadMinioFile(MultipartFile file){
        String filename = file.getOriginalFilename();
        HashMap<String, Object> map = new HashMap<>();
        //替换图片名称
        filename = UUID.randomUUID().toString().toString()+
                filename.substring(filename.lastIndexOf("."));
        long size = file.getSize();//获取文件长度
        if(size>1*1024*1024){
            map.put("code",10001);
            map.put("msg","文件长度不能大于1MB");
            return map;
        }
        MinioClient build = MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .build();
        try {
            build.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(filename)
                    .contentType(MinioUploadEnum.getContentType(filename))
                    .stream(file.getInputStream(),file.getSize(),-1)
                    .build());

        }catch (Exception e){
            e.printStackTrace();
        }

        map.put("data",minioConfig.getEndpoint()+"/"+minioConfig.getBucket()+"/"+filename);//发送路径给前端
        return map;
    }
}
