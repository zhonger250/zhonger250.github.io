package com.example.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/2/28 12:21
 * @Description:
 */
public class ExportUtils {
    public static void poiUtils(Object o, List list) throws Exception {
        //获取对象的类对象
        Class<?> aClass = o.getClass();
        // 创建一个List， 用来存放对象属性的get方法名
        List<String> methodNameList = new ArrayList<>();
        // 获取对象的所有属性
        Field[] fields = aClass.getDeclaredFields();
        // 遍历获取的所有属性的get方法名并存在一个数组中
        for (Field field : fields) {
            String fileName = field.getName();
            String getMethodName = "get" + fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
            System.out.println(getMethodName);
            methodNameList.add(getMethodName);
        }

        // 创建一个excel文件
        HSSFWorkbook sheets = new HSSFWorkbook();
        // 创建文件里的一个sheet
        HSSFSheet sheet = sheets.createSheet(aClass.getSimpleName());
        // 创建第一行, 索引为0 (存放列名)
        HSSFRow row = sheet.createRow(0);
        // 声明单元
        HSSFCell cell = null;
        // 表头, 第一行, 索引从0开始, 存放列名即数据库中的字段名
        for (int i = 0; i < fields.length; i++) {
            // 创建单元
            cell = row.createCell(i);
            // 填入数据
            cell.setCellValue(fields[i].toString());
        }

        // 行
        for (int i = 1; i < list.size(); i++) {
            // 创建行
            row = sheet.createRow(i);
            // 列
            for (int j = 0; j < fields.length; j++) {
                // 创建单元
                cell = row.createCell(j);
                // 反射得到方法名
                Method declaredMethod = aClass.getDeclaredMethod(methodNameList.get(j), (Class<?>) null);
                // invoke : 调用
                Object invoke = declaredMethod.invoke(list.get(i), null);
                System.out.println(invoke);
                cell.setCellValue(invoke.toString());
            }
            System.out.println();
        }

        String xlsName = aClass.getName();
        //这里涉及到“.”在正则表达式中的特殊性，将在下一篇文章中讨论
        String replace = xlsName.replace(".", "/");
        String[] split = replace.split("/");
        System.out.println(split.length);
        String s = split[split.length - 1];
        sheets.write(Files.newOutputStream(Paths.get("X:\\" + s + ".xls")));
    }
}
