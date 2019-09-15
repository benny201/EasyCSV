package org.benny.util.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @Author benny
 * @Date 19/9/13
 * @Description utils for operating files
 **/
public class FileUtil {

    public static final String CSV_TYPE = ".csv";
    public static final String ZIP_TYPE = ".zip";
    private static final String[] SIZE_UNITS = new String[]{"B", "KB", "MB", "GB", "TB"};

    private FileUtil() {
    }

    /**
     * get full file path
     *
     * @param path     full path without file name
     * @param fileName file name without extension
     * @param fileType file extension
     * @return full path
     */
    public static String getFileFullPath(String path, String fileName, String fileType) {
        return getFileFullPath(path, fileName + fileType);
    }

    /**
     * get full file path
     *
     * @param path     full path without file name
     * @param fileName file name with extension
     * @return full path
     */
    public static String getFileFullPath(String path, String fileName) {
        // check if file name was legal
        getFileExtension(fileName);
        return path + File.separator + fileName;
    }


    /**
     * separate file name from path
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        String[] pathStr = path.split(File.separator);
        return pathStr.length == 1 ? pathStr[0] : pathStr[pathStr.length - 1];
    }

    /**
     * get extension of a file, .csv/.zip...etc
     *
     * @param fileName file name with extension
     * @return extension
     */
    public static String getFileExtension(String fileName) {
        String[] dots = fileName.split("\\.");
        if (dots.length > 2) {
            throw new IllegalArgumentException("illegal file name: " + fileName);
        }
        if (dots.length == 1) {
            throw new IllegalArgumentException("illegal file name without extension: " + fileName);
        }
        return "." + dots[1];
    }

    /**
     * convert length to size
     * @param size
     */
    public static String getFileSize(long size) {
        if (size <= 0) return "0";
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + SIZE_UNITS[digitGroups];
    }


    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        String path = "f.csv";
        System.out.println(path);
        System.out.println(getFileExtension(path));
    }

}
