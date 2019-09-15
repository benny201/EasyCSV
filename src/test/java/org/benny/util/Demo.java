package org.benny.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.benny.util.annotations.CSVColumn;

import java.io.Serializable;

/**
 * @Author benny
 * @Date 19/9/14
 * @Description TODO
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Demo implements Serializable{
    private static final long serialVersionUID = -390518098324231014L;
    @CSVColumn(name = "test1", index = 0)
    private String test1;
    @CSVColumn(name = "test3")
    private String test3;
    @CSVColumn(name = "test4")
    private String test4;
    @CSVColumn(name = "test2", index = 1)
    private String test2;
}
