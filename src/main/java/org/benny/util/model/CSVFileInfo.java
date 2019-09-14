package org.benny.util.model;

import lombok.*;

/**
 * @Author benny
 * @Date 19/9/13
 * @Description csv file info model
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CSVFileInfo extends BaseFileInfo{
    private static final long serialVersionUID = 715229374901531743L;
    /**
     * total amount of this file's row
     */
    private Long rowSize;
}
