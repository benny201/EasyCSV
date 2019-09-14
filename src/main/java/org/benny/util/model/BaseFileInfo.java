package org.benny.util.model;

import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * @Author benny
 * @Date 19/9/13
 * @Description Exported File Info
 **/
@Data
public class BaseFileInfo implements Serializable{
    private static final long serialVersionUID = 4873758013859540067L;
    /**
     * file name with file type extension
     */
    private String fileName;
    /**
     * full path of the file
     */
    private String filePath;
    /**
     * file's type, .csv/.zip...etc
     */
    private String fileType;
    /**
     * file length
     */
    private Long fileLength;
    /**
     * convert file length to B/KB/M... etc
     */
    private String fileSize;

}
