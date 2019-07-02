package com.igeek.ebuy.manager.controller;

import com.github.pagehelper.PageInfo;
import com.igeek.ebuy.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hftang
 * @date 2019-07-01 15:30
 * @desc 图片上传
 */
@Controller
public class PictuerController {
    @Value("${FASTDFS_SERVER}")
    private String FASTDFS_SERVER;

    @Autowired
    private FastDFSClient fastDFSClient;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map uploadFile(MultipartFile uploadFile) {
        Map map = new HashMap<>();
        String originalFilename = uploadFile.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        try {
            String uploadFileurl = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            map.put("error", 0);
            map.put("url", FASTDFS_SERVER + uploadFileurl);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", 1);
            map.put("message", "出错了");
        }


        return map;
    }


}
