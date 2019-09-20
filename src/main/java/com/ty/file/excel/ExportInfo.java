package com.ty.file.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @auther: maven
 * @date: 2019/5/23 15:50
 * @description: 自动备货导出 Excel 时使用的映射实体类，Excel 模型
 */
@Data
@Accessors(chain = true)
public class ExportInfo extends BaseRowModel {
    /**
     * 库存型号
     */
    @ExcelProperty(value = "库存型号", index = 0)
    private String partNo;

    /**
     * 库存数量
     */
    @ExcelProperty(value = "库存数量", index = 1)
    private String totalStockQty;

    /**
     * 库存金额
     */
    @ExcelProperty(value = "库存金额", index = 2)
    private String totalAmount;

    /**
     * 库存均价
     */
    @ExcelProperty(value = "库存均价", index = 3)
    private String averagePrice;

    /**
     * 库存平均天数
     */
    @ExcelProperty(value = "库存平均天数", index = 4)
    private String averageDays;

    /**
     * 出库数量QT
     */
    @ExcelProperty(value = "出库数量QT", index = 5)
    private String outNumberQT;

    /**
     * 出库金额QT
     */
    @ExcelProperty(value = "出库金额QT", index = 6)
    private String outAmountQT;

    /**
     * 出库利润QT
     */
    @ExcelProperty(value = "出库利润QT", index = 7)
    private String outProfitsQT;

    /**
     * 客户数量
     */
    @ExcelProperty(value = "客户数量", index = 8)
    private String customerNumber;

    /**
     * 出库数量QTF
     */
    @ExcelProperty(value = "出库数量QTF", index = 9)
    private String outNumberQTF;

    /**
     * 出库金额QTF
     */
    @ExcelProperty(value = "出库金额QTF", index = 10)
    private String outAmountQTF;
    /**
     * 出库利润QTF
     */
    @ExcelProperty(value = "出库利润QTF", index = 11)
    private String outProfitsQTF;

    /**
     * 客户数量F
     */
    @ExcelProperty(value = "客户数量F", index = 12)
    private String customerNumberF;


    /**
     * 出库数量Q4
     */
    @ExcelProperty(value = "出库数量Q4", index = 13)
    private String utNumberQ4;
    /**
     * 出库数量Q3
     */
    @ExcelProperty(value = "出库数量Q3", index = 14)
    private String utNumberQ3;
    /**
     * 出库数量Q2
     */
    @ExcelProperty(value = "出库数量Q2", index = 15)
    private String utNumberQ2;
    /**
     * 出库数量Q1
     */
    @ExcelProperty(value = "出库数量Q1", index = 16)
    private String utNumberQ1;

    /**
     * 销售未交数量
     */
    @ExcelProperty(value = "销售未交数量", index = 17)
    private String salesOverdueNumber;

    /**
     * 等效销售未交数量
     */
    @ExcelProperty(value = "等效销售未交数量", index = 18)
    private String equivalentSalesOverdueNumber;

    /**
     * 等效销售未交金额
     */
    @ExcelProperty(value = "等效销售未交金额", index = 19)
    private String equivalentSalesOverdueAmount;

    /**
     * 采购未交数量
     */
    @ExcelProperty(value = "采购未交数量", index = 20)
    private String procurementOverdueNumber;

    /**
     * 采购未交金额
     */
    @ExcelProperty(value = "采购未交金额", index = 21)
    private String procurementOverdueAmount;

    /**
     * 采购待生效未交数量
     */
    @ExcelProperty(value = "采购待生效未交数量", index = 22)
    private String procurementWaitEffectiveOverdueNumber;

    /**
     * 销售生效未交数量L
     */
    @ExcelProperty(value = "销售生效未交数量L", index = 23)
    private String salesOverdueNumberL;

    /**
     * 销售生效未交数量TL
     */
    @ExcelProperty(value = "销售生效未交数量TL", index = 24)
    private String salesOverdueNumberTL;

    /**
     * 销售超期未交数量
     */
    @ExcelProperty(value = "销售超期未交数量", index = 25)
    private String salesOverTimeOverdueNumber;

    /**
     * 销售近期未交数量
     */
    @ExcelProperty(value = "销售近期未交数量", index = 26)
    private String salesNearTimeOverdueNumber;

    /**
     * 采购超期未交数量
     */
    @ExcelProperty(value = "采购超期未交数量", index = 7)
    private String procurementOverTimeOverdueNumber;

    /**
     * 采购近期未交数量
     */
    @ExcelProperty(value = "采购近期未交数量", index = 28)
    private String procurementNearTimeOverdueNumber;

    /**
     * 需要采购数量
     */
    @ExcelProperty(value = "需要采购数量", index = 29)
    private Double needProcurementNumber;

    /**
     * 需要采购倍率
     */
    @ExcelProperty(value = "需要采购倍率", index = 30)
    private Double needProcurementRatio;

    /**
     * baseLine
     */
    @ExcelProperty(value = "baseLine", index = 31)
    private Double baseLine;
}
