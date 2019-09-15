package org.benny.util;

import static org.junit.Assert.assertTrue;

import org.benny.util.model.CSVFileInfo;
import org.benny.util.utils.FileUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void exportCSV() {
        List<Demo> input = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            input.add(Demo.builder().test1("1").test3("2").build());
        }
        CSVFileInfo csvFileInfo = ExportCSVUtils.export(input, Demo.class, "test.csv");
        System.out.println(csvFileInfo);
    }
}
