package com.ty.file.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import com.ty.common.util.ApiResult;
import com.ty.common.util.TimeUtils;
import com.ty.common.util.UUIDUtil;
import com.ty.common.util.UserUtil;
import com.ty.file.config.OssProperties;
import com.ty.file.util.FtpUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther: maven
 * @date: 2019/9/16 16:49
 * @description: 文件上传控制器
 */
@Slf4j
@RestController
@RequestMapping("/fileUpload")
@Api(value = "系统文件上传controller", tags = {"整个系统文件上传操作接口"})
public class FileUploadController {

    // 文件在服务器端保存的主目录
    @Value("${FTP.BASEPATH}")
    private String basePath;

    // 访问图片时的基础url
    @Value("${IMAGE.BASE.URL}")
    private String baseUrl;

    // 服务运行环境
    @Value("${spring.profiles.active}")
    private String active;

    @Autowired
    private FtpUtil ftpUtil;

    //@Autowired
    private OSS ossClient;

    @Autowired
    private OssProperties ossProperties;

    final static String bucketName = "";

    /**
     * 上传多个文件
     *
     * @param request
     * @return
     */
    @PostMapping("/uploadFiles")
    public ApiResult<Map<String, String>> saveFiles(HttpServletRequest request, String fileType) {
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
                MultipartHttpServletRequest.class);

        List<MultipartFile> uploadFiles = multipartRequest.getFiles("file");
        Map<String, String> resultMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(uploadFiles)) {
            // 上传文件的数量
            final ExecutorService exec = Executors.newFixedThreadPool(uploadFiles.size());
            final CountDownLatch latch = new CountDownLatch(uploadFiles.size());
            uploadFiles.forEach(multipartFile -> {
                Callable run = runCallable(request, fileType, multipartFile, resultMap, latch);
                exec.submit(run);
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            //任务执行完毕后 关闭线程池 不再接收新任务
            exec.shutdown();
        }
        return new ApiResult<>(resultMap);
    }

    private Callable runCallable(HttpServletRequest request, String fileType, MultipartFile multipartFile, Map<String, String> resultMap, CountDownLatch latch) {
        return new Callable() {
            @Override
            public Object call() {
                try {
                    //给上传的图片生成新的文件名
                    //获取原始文件名
                    String oldName = multipartFile.getOriginalFilename();
                    //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
                    String newName = UUIDUtil.genImageName();
                    newName = newName + oldName.substring(oldName.lastIndexOf("."));

                    //1.3生成文件在服务器端存储的子目录
                    String companyCode = UserUtil.getCompanyCode(request);
                    String filePath = "/" + companyCode + "/" + fileType + "/" + TimeUtils.getCurrentTime("yyyyMMdd");

                    //3、把图片上传到图片服务器
                    //3.1获取上传的io流
                    InputStream input = multipartFile.getInputStream();

                    //判断不是生产环境就调用FtpUtil进行上传
                    if (!"prod".equals(active)) {
                        boolean result = ftpUtil.uploadFile(basePath, filePath, newName, input);
                        if (result) {
                            resultMap.put(multipartFile.getOriginalFilename(), baseUrl + filePath + "/" + newName);
                        }
                    } else {
                        //生产环境上传到aliyun oss
                        String key = companyCode + "/" + fileType + "/" + TimeUtils.getCurrentTime("yyyyMMdd") + "/" + newName;
                        PutObjectResult result = ossClient.putObject(bucketName, key, new BufferedInputStream(input));
                        int statusCode = result.getResponse().getStatusCode();
                        if (statusCode == 200) {
                            resultMap.put(multipartFile.getOriginalFilename(), bucketName + "." + ossProperties.getEndpoint() + "/" + key);
                        }
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                } finally {
                    latch.countDown();
                }
                return null;
            }
        };
    }

    /**
     * 上传单个文件
     *
     * @param request
     * @return
     */
    @PostMapping("/uploadFile")
    public ApiResult<String> saveFile(HttpServletRequest request, String fileType) throws Exception {
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
                MultipartHttpServletRequest.class);

        MultipartFile uploadFile = multipartRequest.getFile("file");
        //给上传的图片生成新的文件名
        //获取原始文件名
        String oldName = uploadFile.getOriginalFilename();
        //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
        String newName = UUIDUtil.genImageName();
        newName = newName + oldName.substring(oldName.lastIndexOf("."));

        //1.3生成文件在服务器端存储的子目录
        String companyCode = UserUtil.getCompanyCode(request);
        String filePath = "/" + companyCode + "/" + fileType + "/" + TimeUtils.getCurrentTime("yyyyMMdd");

        //3、把图片上传到图片服务器
        //3.1获取上传的io流
        InputStream input = uploadFile.getInputStream();
        if (!"prod".equals(active)) {
            //3.2调用FtpUtil工具类进行上传
            boolean result = ftpUtil.uploadFile(basePath, filePath, newName, input);
            if (result) {
                return new ApiResult<>(baseUrl + filePath + "/" + newName);
            } else {
                return new ApiResult<>("500");
            }
        } else {
            String key = companyCode + "/" + fileType + "/" + TimeUtils.getCurrentTime("yyyyMMdd") + "/" + newName;
            PutObjectResult result = ossClient.putObject(bucketName, key, new BufferedInputStream(input));
            int statusCode = result.getResponse().getStatusCode();
            if (statusCode == 200) {
                return new ApiResult<>(bucketName + "." + ossProperties.getEndpoint() + "/" + key);
            }
        }
        return new ApiResult<>("500", "fail");
    }
}
