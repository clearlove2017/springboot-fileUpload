package com.ty.file.service;

import com.ty.common.vo.BacklogDTO;
import com.ty.file.service.impl.EasyexcelServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @auther: maven
 * @date: 2019/5/23 15:59
 * @description:
 */
@FeignClient(name = "TYICERP-services-erp-auto", fallback = EasyexcelServiceFallback.class)
public interface EasyexcelService {
    /**
     * 分页查询备货数据
     *
     * @return
     */
    @GetMapping("/auto/loadBacklogList")
    List<BacklogDTO> loadBacklogList();
}
