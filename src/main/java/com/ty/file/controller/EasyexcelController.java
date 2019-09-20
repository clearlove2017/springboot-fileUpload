package com.ty.file.controller;

import com.ty.common.exception.ExcelException;
import com.ty.common.vo.BacklogDTO;
import com.ty.file.excel.ExcelUtil;
import com.ty.file.excel.ExportInfo;
import com.ty.file.service.EasyexcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @auther: maven
 * @date: 2019/5/23 15:08
 * @description: exce上传下载处理controller
 */
@RestController
@RequestMapping("/excel")
@Api(value = "exce上传下载controller", tags = {"exce上传下载操作接口"})
public class EasyexcelController {

    @Autowired
    private EasyexcelService easyexcelService;

    /**
     * 导出 Excel（一个 sheet）
     */
    @ApiOperation(value = "自动备货数据报表excel下载", notes = "自动备货数据报表excel下载", httpMethod = "GET")
    @ApiImplicitParam(name = "list", value = "自动备货数据", required = true, dataType = "List")
    @GetMapping(value = "writeExcel")
    public void writeExcel(HttpServletResponse response) throws ExcelException {
        List<BacklogDTO> backlogDTOS = easyexcelService.loadBacklogList();
        List<ExportInfo> exportInfos = Collections.synchronizedList(new ArrayList<>());
        backlogDTOS.forEach(backlogDTO -> {
            ExportInfo exportInfo = new ExportInfo();
            BeanUtils.copyProperties(backlogDTO, exportInfo);
            exportInfos.add(exportInfo);
        });
        String fileName = "自动备货 Excel 文件";
        String sheetName = "自动备货 sheet";
        ExcelUtil.writeExcel(response, exportInfos, fileName, sheetName, new ExportInfo());
    }
}
