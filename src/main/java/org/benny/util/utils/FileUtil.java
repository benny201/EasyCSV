package org.benny.util.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @Author benny
 * @Date 19/9/13
 * @Description utils for operating files
 **/
public class FileUtil {

    public static final String CSV_TYPE = ".csv";
    public static final String ZIP_TYPE = ".zip";

    private FileUtil() {
    }

    /**
     * get full file path
     * @param path full path without file name
     * @param fileName file name without extension
     * @param fileType file extension
     * @return full path
     */
    public static String getFileFullPath(String path, String fileName, String fileType) {
        return getFileFullPath(path, fileName + fileType);
    }

    /**
     * get full file path
     * @param path full path without file name
     * @param fileName file name with extension
     * @return full path
     */
    public static String getFileFullPath(String path, String fileName) {
        // check if file name was legal
        getFileExtension(fileName);
        return path + File.separator + fileName;
    }

    /**
     * get extension of a file, .csv/.zip...etc
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

    public static void main(String[] args) {
        System.out.println(getFileExtension("f.csv"));
    }

}
