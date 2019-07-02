package com.igeek.ebuy.util;

        import org.csource.common.MyException;
        import org.csource.fastdfs.*;

        import java.io.IOException;
        import java.security.PublicKey;

/**
 * @author hftang
 * @date 2019-07-01 14:19
 * @desc fastdfs上传照片的工具类
 */
public class FastDFSClient {

    private String confPath = null;

    public FastDFSClient(String confPath) {
        this.confPath = confPath;

        //分2种情况，第一种是相对路径 第二种是绝对路径

        if (confPath.indexOf("classpath") != -1) {

            String path = confPath.substring(confPath.indexOf("classpath") + 10);
            String path1 = FastDFSClient.class.getClassLoader().getResource(path).getPath();

            System.out.println(path1);

            try {
                ClientGlobal.init(path1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MyException e) {
                e.printStackTrace();
            }


        } else {
            //绝对路径
            try {
                ClientGlobal.init(confPath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MyException e) {
                e.printStackTrace();
            }

        }

    }

    public String uploadFile(String fileName, String ext) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        String[] strings = storageClient.upload_file(fileName, ext, null);

        return strings[0] + "/" + strings[1];
    }

    public String uploadFile(byte[] fileName, String ext) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        String[] strings = storageClient.upload_file(fileName, ext, null);

        return strings[0] + "/" + strings[1];
    }
}
