package com.canglang.common.excel;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author leitao.
 * @category
 * @time: 2019/1/17 0017-14:11
 * @version: 1.0
 * @description:
 **/
public class ReadExcel {

    private static ThreadPoolExecutor bizExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(), new ThreadPoolExecutor.CallerRunsPolicy());


    public static void main(String[] arge) throws Exception {
        long start = System.currentTimeMillis();
        InputStream inputStream = getNetFile();
//        InputStream inputStream = getLocalFile();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheetOne = workbook.getSheetAt(0);
        Preconditions.checkArgument(sheetOne.getLastRowNum() >= 1, "excel内容不能为空！");
        int column = 2;
        Row titleRow = sheetOne.getRow(0);
        int columnNum = titleRow.getLastCellNum();
        Preconditions.checkArgument(columnNum == column, String.format("上传的模板为%s列不符合文轩模板%s列，请使用文轩模板上传！", columnNum, column));
        System.out.println("总行数:" + sheetOne.getLastRowNum());
        List<Map<String, String>> list = Lists.newArrayList();
        for (int i = 0; i <= sheetOne.getLastRowNum(); i++) {
            Map<String, String> map = Maps.newHashMap();
            Row row = sheetOne.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                Preconditions.checkArgument(cell != null, String.format("解析excel时，单元格[%s,%s]为空！请不要在单元格中留下空白格！", i + 1, j + 1));
                // 此处设置单元格数据类型为字符串，后面统一从字符串转换不会那么多的坑
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String cellValue = cell.getStringCellValue();
                if (j == 0) {
                    map.put("productsaleid", cellValue);
                }
                if (j == 1) {
                    map.put("outitemid", cellValue);
                }
//                System.out.println(cellValue);
            }
            list.add(map);
        }
        long middle = System.currentTimeMillis();
        System.out.println("读取文件耗时:" + (middle - start));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String, String>> tempList = list;
                for (Map<String, String> map : tempList) {
                    bizExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Map<String, String> tempMap = map;
                            System.out.println(tempMap.get("productsaleid") + ":" + tempMap.get("outitemid"));
                        }
                    });
                }
                bizExecutor.shutdown();
            }
        });
        thread.start();
        System.out.println("异步处理耗时:" + (System.currentTimeMillis() - middle));
    }

    private static InputStream getLocalFile(){
        InputStream inputStream = ReadExcel.class.getClassLoader().getResourceAsStream("test_excel.xlsx");
        return inputStream;
    }
    private static InputStream getNetFile() throws Exception{
        String httpPath = "http://files.test.winxuan.io/common/181018/b38818cbd6fe4783997f877a08e1774b.xls";
        URL url =new URL(httpPath); // 创建URL
        URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码
        urlconn.connect();
        HttpURLConnection httpconn =(HttpURLConnection)urlconn;
        InputStream inputStream = httpconn.getInputStream();
        return inputStream;
    }

}
