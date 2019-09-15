package org.benny.util;

import lombok.Cleanup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;
import org.benny.util.annotations.CSVColumn;
import org.benny.util.model.BaseRowModel;
import org.benny.util.model.CSVFileInfo;
import org.benny.util.utils.FileUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author benny
 * @Date 19/9/13
 * @Description CSV Export Utils
 **/
public class ExportCSVUtils {

    /**
     * max rows for one csv file by default, if need separate data to multi files
     */
    public final int MAX_ROWS_FOR_ONE_CSV_FILE = 10000000;

    /**
     * export csv file name with random name and fix path
     *
     * @param dataSet    input data set
     * @param inputClass input Class
     */
    public static <T> CSVFileInfo export(List<T> dataSet, Class<T> inputClass) {
        String filePath = FileUtil.getUUID() + FileUtil.CSV_TYPE;
        return export(dataSet, inputClass, filePath);
    }

    /**
     * export csv file
     *
     * @param dataSet    input data set
     * @param inputClass input Class
     * @param fullPath   full path with file name and it's extension, only .csv
     */
    public static <T> CSVFileInfo export(List<T> dataSet, Class<T> inputClass, String fullPath) {
        // 1. get header of the csv file
        BaseRowModel baseRowModel = getHeaderInfo(inputClass);
        // 2. get content and write to csv
        return writeCSV(fullPath, dataSet, baseRowModel);
    }

    private static <T> CSVFileInfo writeCSV(String filePath,
                                            List<T> dataList,
                                            BaseRowModel rowInfo) {
        CSVFileInfo csvFileInfo = new CSVFileInfo();

        try {
            @Cleanup FileOutputStream out = new FileOutputStream(filePath);
            @Cleanup OutputStreamWriter osw = new OutputStreamWriter(out, "GBK");
            @Cleanup BufferedWriter bw = new BufferedWriter(osw);
            Object[] row = new Object[rowInfo.getHeaders().length];

            // write header
            bw.append(constructRow(rowInfo.getHeaders())).append("\r");
            for (int i = 0; i < dataList.size(); i++) {
                T data = dataList.get(i);
                // get row content
                getRowContent(data, row, rowInfo);
                // write row
                bw.append(constructRow(row)).append("\r");
            }
            bw.flush();

            // file info
            File resultFile = new File(filePath);
            csvFileInfo.setRowSize(dataList.size());
            csvFileInfo.setFileLength(resultFile.length());
            csvFileInfo.setFilePath(filePath);
            csvFileInfo.setFileName(FileUtil.getFileName(filePath));
            csvFileInfo.setFileSize(FileUtil.getFileSize(resultFile.length()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvFileInfo;
    }

    /**
     * convert array to data row
     * @param row data array
     */
    private static String constructRow(Object[] row) {
        return StringUtils.join(row, ",");
    }


    /**
     * export by apache commons-csv
     *
     * @param filePath full path for exported csv file
     * @param dataList content
     * @param rowInfo  csv file base info
     */
    private static <T> CSVFileInfo writeCSVByCommonCSV(String filePath,
                                                       List<T> dataList,
                                                       BaseRowModel rowInfo) {

        CSVFileInfo csvFileInfo = new CSVFileInfo();
        try {
            @Cleanup FileOutputStream fos = new FileOutputStream(filePath);
            @Cleanup OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
            CSVFormat csvFormat = CSVFormat.DEFAULT.withIgnoreHeaderCase(true).withHeader(rowInfo.getHeaders());
            CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);
            Object[] row = new Object[rowInfo.getHeaders().length];
            for (int i = 0; i < dataList.size(); i++) {
                T data = dataList.get(i);
                // get row content
                getRowContent(data, row, rowInfo);
                // write row
                csvPrinter.printRecord(row);
            }
            // file info
            File resultFile = new File(filePath);
            csvFileInfo.setRowSize(dataList.size());
            csvFileInfo.setFileLength(resultFile.length());
            csvFileInfo.setFilePath(filePath);
            csvFileInfo.setFileName(FileUtil.getFileName(filePath));
            csvFileInfo.setFileSize(FileUtil.getFileSize(resultFile.length()));
            // clear printer
            csvPrinter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvFileInfo;
    }

    private static <T> void getRowContent(T data, Object[] row, BaseRowModel rowInfo) throws Exception {
        Field[] fields = data.getClass().getDeclaredFields();
        int defaultIndexCol = rowInfo.getResortedIndexMap().size();
        for (Field field : fields) {
            field.setAccessible(true);
            CSVColumn column = field.getAnnotation(CSVColumn.class);
            // not null
            if (column != null) {
                int index = column.index();
                if (index != Integer.MIN_VALUE) {
                    row[rowInfo.getResortedIndexMap().get(index)] = field.get(data);
                } else {
                    row[defaultIndexCol++] = field.get(data);
                }
            }
        }
    }


    private static <T> BaseRowModel getHeaderInfo(Class<T> input) {
        Field[] fields = input.getDeclaredFields();
        Map<Integer, String> indexHeaderMap = new TreeMap<>();
        List<String> defaultIndexHeader = new ArrayList<>();
        for (Field field : fields) {
            CSVColumn column = field.getAnnotation(CSVColumn.class);
            // not null &
            if (column != null) {
                // not default column index
                if (column.index() != Integer.MIN_VALUE) {
                    if (indexHeaderMap.containsKey(column.index())) {
                        throw new IllegalArgumentException("Same Index For: " + column.index());
                    }
                    // put map
                    indexHeaderMap.put(column.index(), column.name());
                } else {
                    // for default index, append to the sorted index
                    defaultIndexHeader.add(column.name());
                }
            }
        }

        // resort the index for some missing value between two indexes
        Map<Integer, Integer> resortedIndexMap = new HashMap<>();
        List<String> header = new ArrayList<>();

        indexHeaderMap.keySet().forEach(key -> {
            header.add(indexHeaderMap.get(key));
            resortedIndexMap.put(key, header.size() - 1);
        });

        header.addAll(defaultIndexHeader);

        BaseRowModel rowModel = new BaseRowModel();
        rowModel.setHeaders(header.toArray(new String[header.size()]));
        rowModel.setResortedIndexMap(resortedIndexMap);
        return rowModel;
    }


}
