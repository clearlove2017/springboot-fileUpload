package com.ty.file.util;

import com.ty.file.factory.FtpClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * @auther: maven
 * @date: 2019/9/17 10:08
 * @description: 实现文件上传下载, 采用ftp连接池的方式
 */
@Slf4j
public class FtpUtil {

    private GenericObjectPool<FTPClient> ftpClientPool;

    public FtpUtil(FtpClientFactory ftpClientFactory) {
        this.ftpClientPool = new GenericObjectPool<>(ftpClientFactory);
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param basePath FTP服务器基础目录,/home/ftpuser
     * @param filePath FTP服务器文件存放路径。companycode+filetype+日期。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(String basePath, String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftpClient = null;
        BufferedInputStream inStream = null;
        try {
            //从池中获取对象
            ftpClient = ftpClientPool.borrowObject();
            // 验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.warn("ftpServer refused connection, replyCode:{}", replyCode);
                return result;
            }
            //切换到上传目录
            if (!ftpClient.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    String workingDirectory = ftpClient.printWorkingDirectory();
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(tempPath)) {
                        if (!ftpClient.makeDirectory(dir)) {
                            return result;
                        } else {
                            ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            inStream = new BufferedInputStream(input);
            //设置为被动模式
            ftpClient.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件，重试3次
            final int retryTimes = 3;

            for (int j = 0; j <= retryTimes; j++) {
                result = ftpClient.storeFile(filename, inStream);
                if (result) {
                    log.info("upload file success! {}", filename);
                    break;
                }
                log.warn("upload file failure! try uploading again... {} times", j);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inStream);
            //将对象放回池中
            ftpClientPool.returnObject(ftpClient);
        }
        return result;
    }
}
