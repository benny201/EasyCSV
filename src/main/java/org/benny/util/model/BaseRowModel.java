package org.benny.util.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author benny
 * @Date 19/9/13
 * @Description base model for csv row
 **/
@Data
public class BaseRowModel implements Serializable{
    private static final long serialVersionUID = 1024601560857346015L;
    private String[] headers;
    private Map<Integer, Integer> resortedIndexMap;
}
