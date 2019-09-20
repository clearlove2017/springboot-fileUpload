package com.ty.file.service.impl;

import com.ty.common.vo.BacklogDTO;
import com.ty.file.service.EasyexcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: maven
 * @date: 2019/5/23 16:04
 * @description:
 */
@Service
@Slf4j
public class EasyexcelServiceFallback implements EasyexcelService {
    @Override
    public List<BacklogDTO> loadBacklogList() {
        log.error("调用loadBacklogs方法异常!" );
        return null;
    }
}
