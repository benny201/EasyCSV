package org.benny.util;

import lombok.Cleanup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.benny.util.annotations.CSVColumn;
import org.benny.util.model.BaseRowModel;
import org.benny.util.model.CSVFileInfo;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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

    public static <T> CSVFileInfo export(List<T> dataSet, Class<T> inputClass) {
        return null;
    }


    /**
     * export csv file
     * @param dataSet input data set
     * @param inputClass input Class
     * @param fullPath full path with file name and it's extension, only .csv
     * @return
     */
    public static <T> CSVFileInfo export(List<T> dataSet, Class<T> inputClass, String fullPath) {
        CSVFileInfo resultFile = new CSVFileInfo();
        // 1. get header of the csv file
        BaseRowModel baseRowModel = getHeaderInfo(inputClass);
        // 2. get content and write to csv
        writeCSV(fullPath, dataSet, baseRowModel);
        return resultFile;
    }


    /**
     * export by apache commons-csv
     * @param filePath full path for exported csv file
     * @param dataList content
     * @param rowInfo  csv file base info
     */
    private static <T> void writeCSV(String filePath,
                                    List<T> dataList,
                                    BaseRowModel rowInfo) {
        try {
            @Cleanup FileOutputStream fos = new FileOutputStream(filePath);
            @Cleanup OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(rowInfo.getHeaders());
            CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);
            Object[] row = new Object[rowInfo.getHeaders().length];
            for (int i = 0; i < dataList.size(); i++) {
                T data = dataList.get(i);
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
                // write row
                csvPrinter.printRecord(row);
            }
            csvPrinter.flush();
        } catch (Exception e) {
            e.printStackTrace();
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
