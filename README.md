# EasyCSV
基于JAVA的CSV导入导出
A library for import &amp; export CSV in JAVA, Easy to use!


## Quick Start
### 1.Export 导出
1) add **@CSVColumn** annotation to exported attributes
* index is not necessary, but without declared index, this column will be appended to the tail.

```java
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
```

2) call **ExportCSVUitls**

```java
CSVFileInfo csvFileInfo = ExportCSVUtils.export(input, Demo.class, "test.csv");
```

### 2. Import 导入

```
see you soon...
```

### 3. Import this lib
Not yet upload to the maven repo, so you should download the source code, and install locally.

Then import this Maven coordinates：
```
    <dependency>
      <groupId>org.benny.util</groupId>
      <artifactId>easycsv</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>

```

### 4. Contact Me
Email me: zhibinwu1993@outlook.com

