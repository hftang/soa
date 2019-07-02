package com.igeek.web;

import com.igeek.ebuy.util.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @author hftang
 * @date 2019-07-01 11:16
 * @desc
 */
public class test {

    //测试fastdfs

//    @Test
    public void test001() {
        try {
            ClientGlobal.init("D:\\javaweb\\ebuy-parent\\buy-manager-web\\src\\test\\resources\\client.conf");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            String[] pngs = storageClient.upload_file("C:\\Users\\61777\\Pictures\\thumb.png", "png", null);
            for (int i = 0; i < pngs.length; i++) {
                System.out.println(pngs[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

    }


//    @Test
    public void test002(){
        FastDFSClient fastDFSClient = new FastDFSClient("D:\\javaweb\\ebuy-parent\\buy-manager-web\\src\\test\\resources\\client.conf");
        try {
            String file = fastDFSClient.uploadFile("C:\\Users\\61777\\Pictures\\Camera Roll\\hetang.jpg", "jpg");
            System.out.println(file);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
