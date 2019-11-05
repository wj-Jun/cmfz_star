package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Tset1 {

    public static void main(String[] args) throws Exception {
        //创建excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet  sheet = workbook.createSheet("测试");
        //创建行
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        //写值
        cell.setCellValue(new Date());
        //将当前workbook文件通过流的方式输出到本地磁盘
        workbook.write(new FileOutputStream(new File("D:/java166/day3/framework/后期项目/test.xls")));

    }
}
